package com.basejava.storage;

import com.basejava.exceptions.NotExistStorageException;
import com.basejava.model.*;
import com.basejava.sql.ConnectionFactory;
import com.basejava.sql.SqlTemplate;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private final SqlTemplate sqlTemplate;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlTemplate = new SqlTemplate(connectionFactory);
    }

    @Override
    public void clear() {
        sqlTemplate.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume resume) {
        sqlTemplate.transactionExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) " +
                    "VALUES (?, ?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO contact (type, value, " +
                    "resume_uuid) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> contact : resume.getContacts()
                        .entrySet()) {
                    statement.setString(1, contact.getKey().name());
                    statement.setString(2, contact.getValue());
                    statement.setString(3, resume.getUuid());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                SectionType sectionType = section.getKey();
                AbstractSection abstractSection = section.getValue();
                switch (sectionType) {
                    case PERSONAL, POSITION -> {
                        TextSection textSection = (TextSection) abstractSection;
                        try (PreparedStatement statement =
                                     connection.prepareStatement("INSERT  INTO text_section " + "(text, " +
                                             "resume_uuid, type) VALUES (?, ?, ?::section_type)")) {
                            statement.setString(1, textSection.getText());
                            statement.setString(2, resume.getUuid());
                            statement.setString(3, sectionType.name());
                            statement.addBatch();
                            statement.executeBatch();
                        }
                    }
                    case QUALIFICATIONS, ACHIEVEMENTS -> {
                        ListSection listSection = (ListSection) abstractSection;
                        String[] bullets = listSection.getTexts()
                                .toArray(new String[0]);
                        try (PreparedStatement statement = connection.prepareStatement("""
                                WITH insert_section AS (
                                          INSERT INTO list_section (resume_uuid, type)
                                          VALUES (?, ?::section_type)
                                          RETURNING id)
                                INSERT
                                  INTO list_content (text, list_section_id)
                                    SELECT text, id
                                      FROM UNNEST(?)
                                        AS text, insert_section;
                                """
                        )) {
                            statement.setString(1, resume.getUuid());
                            statement.setString(2, sectionType.name());
                            statement.setArray(3, connection.createArrayOf("text", bullets));
                            statement.addBatch();
                            statement.executeBatch();
                        }
                    }
                    case EDUCATION, EXPERIENCE -> {
                    }
                }
            }


            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlTemplate.execute("""
                SELECT r.full_name AS full_name,
                       c.id        AS contact_id,
                       c.type      AS contact_type,
                       c.value     AS contact_value,
                       ts.id       AS text_section_id,
                       ts.text     AS text_section_text,
                       ts.type     AS text_section_type,
                       lc.id       AS list_content_id,
                       lc.text     AS list_content_text
                  FROM public.resume r
                           LEFT JOIN contact c ON r.uuid = c.resume_uuid
                           LEFT JOIN text_section ts ON r.uuid = ts.resume_uuid
                           LEFT JOIN list_section ls ON r.uuid = ls.resume_uuid
                           LEFT JOIN list_content lc ON ls.id = lc.list_section_id
                 WHERE r.uuid = ?;
                 """, statement -> {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));

            Map<ContactType, String> processedContacts = new HashMap<>();


            String value = resultSet.getString("value");
            ContactType type = ContactType.valueOf(resultSet.getString("type"));
            resume.addContact(type, value);
            while (resultSet.next()) {
                value = resultSet.getString("value");
                type = ContactType.valueOf(resultSet.getString("type"));
                resume.addContact(type, value);
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlTemplate.execute("DELETE FROM resume WHERE uuid = ?", statement -> {
            statement.setString(1, uuid);
            int deleted = statement.executeUpdate();
            if (deleted == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        sqlTemplate.execute("SELECT * FROM resume r ORDER BY full_name, uuid", statement -> {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String fullName = resultSet.getString("full_name");
                resumes.add(new Resume(uuid, fullName));
            }
            return resumes;
        });
        return resumes;
    }

    @Override
    public int size() {
        return sqlTemplate.execute("SELECT COUNT(*) FROM resume", statement -> {
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlTemplate.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            int updated = statement.executeUpdate();
            if (updated == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }
}

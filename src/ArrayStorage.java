import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_MAX_SIZE = 10000;
    Resume[] storage = new Resume[STORAGE_MAX_SIZE];
    private int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if(isExist(r)) {
            System.out.println("ERROR: The resume with uuid = \"" + r.uuid + "\" already exists.");
            System.out.println("A resume has not been saved");
            return;
        }
        if (size == STORAGE_MAX_SIZE) {
            System.out.println("ERROR: The storage capacity is exceeded.\nNew resume has not been saved.");
            return;
        }
        storage[size++] = r;
    }

    boolean isExist(Resume r) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                return true;
            }
        }
        return false;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        System.out.println("ERROR: The resume with uuid = \"" + uuid + "\" does not exist.");
        return null;
    }

    void delete(String uuid) {
        int indexForDelete = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                indexForDelete = i;
                break;
            }
        }
        if (indexForDelete == -1) {
            System.out.println("ERROR: The resume with uuid = \"" + uuid + "\" was not found.");
            return;
        }
        storage[indexForDelete] = storage[size - 1];
        storage[size--] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}

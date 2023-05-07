package com.basejava.storage;

import com.basejava.model.*;
import com.basejava.util.DateUtil;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {
    private static final String[] PHONE_NUMBERS = {"(555) 123-4567", "(555) 987-6543", "(555) 555-1212", "(555) " +
            "456-7890", "(555) 777-8888", "(555) 999-0000", "(555) 222-3333", "(555) 444-5555", "(555) 888-9999",
            "(555) " + "111" + "-2222"};
    private static final String[] EMAILS = new String[]{"gmail", "yahoo", "hotmail", "outlook", "icloud", "protonmail"
            , "aol", "inbox", "fastmail.com", "hushmail.com"};
    private static final String[] POSITIONS = new String[]{"Serving as a project manager for a team of 10.", "Working"
            + " as a software developer for a fast-growing tech startup.", "Holding a leadership position in a " +
            "non-profit organization.", "Employed as a marketing specialist for a Fortune 500 company.", "Serving as "
            + "a customer service representative for a multinational corporation.",
            "Working as a sales executive " + "for" + " a" + " leading e-commerce platform.",
            "Holding a teaching " + "position at a local university.", "Employed " + "as a " + "human resources " +
            "manager for a global " + "organization.", "Serving as a financial " + "analyst for a " + "major " +
            "investment firm.", "Working " + "as a research assistant for a renowned " + "academic institution."};
    private static final String[] PERSONALS =
            new String[]{"Strong attention to detail and a focus on accuracy in " + "all" + " tasks.",
                    "Excellent " + "problem-solving skills " + "and" + " ability to think creatively.", "Highly" + " "
                    + "organized " + "and " + "able" + " to manage multiple tasks " + "simultaneously.", "Strong " +
                    "communication" + " " + "skills and ability to " + "work " + "effectively with teams.", "Positive"
                    + " " + "attitude and " + "ability" + " " + "to stay motivated even in " + "challenging" + " " +
                    "situations.", "Adaptable and " + "able" + " to " + "quickly " + "learn new skills and" + " " +
                    "processes.", "Ability to " + "work " + "well under " + "pressure " + "and meet " + "tight " +
                    "deadlines.",
                    "Demonstrated " + "leadership " + "skills and " + "ability to motivate " + "others" + ".",
                    "Strong " + "work ethic " + "and " + "commitment to quality in all work.", "Adept at" + " " +
                    "balancing" + " " + "competing priorities and making " + "effective " + "decisions."};
    private static final String[] ACHIEVEMENTS = new String[]{"Increased sales by 25% over a six-month period " +
            "through" + " the implementation of targeted marketing campaigns",
            "Recognized as top-performing sales " + "representative" + " for two consecutive quarters",
            "Developed " + "and executed successful customer retention" + " strategies " + "resulting in a 20% " +
                    "increase in repeat " + "business",
            "Designed and launched a new website " + "resulting in a 30%" + " increase in online traffic",
            "Streamlined internal processes resulting in a 15% " + "reduction in project " + "completion time",
            "Received company-wide recognition for outstanding project " + "management skills",
            "Implemented a new " + "inventory management system resulting in a 50% reduction in " + "inventory " +
                    "discrepancies", "Developed " + "and delivered effective employee training programs resulting in "
            + "improved customer satisfaction" + " scores", "Achieved a perfect safety record by developing and " +
            "enforcing strict safety protocols",
            "Led a team of five software developers to deliver a new software " + "product ahead of schedule and " +
                    "under " + "budget", "Designed and developed a new mobile app that received" + " over 10,000 " +
            "downloads within the first " + "month of release",
            "Implemented new project management " + "processes " + "that resulted in a 25% increase in " + "project " + "completion rates", "Successfully negotiated " + "and " + "closed contracts with several new key " + "clients " + "resulting in a 40% increase in annual revenue", "Developed and implemented a cost-saving " + "initiative that" + " resulted in a 15% reduction in overall " + "expenses", "Received multiple awards " + "for outstanding customer " + "service skills and high " + "levels of client satisfaction", "Developed " + "and launched a new social media " + "campaign resulting in a " + "50% increase in engagement and a 20% " + "increase in website traffic", "Coordinated " + "and executed " + "successful fundraising events " + "resulting in over $100,000 in donations", "Implemented a new " + "employee" + " wellness program " + "resulting in improved employee morale and decreased absenteeism", "Received " + "multiple awards for " + "outstanding performance and high levels of customer satisfaction", "Consistently " + "met" + " and " + "exceeded sales targets resulting in a 30% increase in annual revenue", "Developed and " + "delivered " + "training programs that resulted in improved " + "employee productivity and " + "reduced turnover " + "rates", "Implemented new QA processes that resulted in a 50% reduction " + "in " + "defects and improved product " + "quality", "Developed and executed successful marketing " + "campaigns " + "resulting in a 25% increase in brand " + "awareness", "Successfully launched a new " + "product line " + "resulting in a 40% increase in sales", "Led " + "cross-functional " + "teams to " + "successfully deliver " + "multiple complex projects on time and within budget", "Developed and " + "executed a cost-saving " + "initiative resulting in a 20% reduction in overall expenses", "Received " + "multiple " + "awards for " + "outstanding leadership skills and ability to drive results", "Developed " + "and delivered " + "training " + "programs that resulted in improved employee skills and " + "competencies", "Received multiple awards" + " for outstanding innovation and creativity in " + "product " + "development", "Successfully managed and " + "delivered " + "a large-scale project resulting in a 15% " + "increase in " + "customer satisfaction"};
    private static final String[] QUALIFICATIONS = new String[]{"Excellent communication skills", "Effective " +
            "teamwork" + " and collaboration", "Proven ability to meet deadlines", "Strong problem-solving and " +
            "analytical skills", "Proficient in Microsoft Office Suite", "Experience with project management " +
            "software", "Attention to " + "detail and accuracy", "Strong organizational and time management skills",
            "Ability to adapt to changing " + "priorities", "Experience in customer service and client relations",
            "Ability to work independently and " + "take initiative", "Ability to learn quickly and apply new " +
            "concepts", "Experience in data analysis and " + "interpretation", "Excellent research and writing " +
            "skills", "Proficiency in a second language", "Ability " + "to work in a fast-paced environment",
            "Experience in public speaking and presentations", "Knowledge of " + "industry-specific software",
            "Ability to think creatively and outside the box", "Strong leadership and " + "decision-making skills",
            "Experience in budget management", "Passion for continuous learning and " + "professional development",
            "Ability to build and maintain positive relationships with colleagues and " + "clients",
            "Expertise in " + "problem identification and resolution", "Experience in sales and marketing", "Ability "
            + "to work well under" + " pressure", "Strong customer service skills"};
    private static final String[] TITLES = {"Marketing Manager", "Software Engineer", "Operations Coordinator",
            "Sales Associate", "Graphic Designer", "Financial Analyst", "Human Resources Specialist", "Product " +
            "Manager", "Customer Service Representative", "Web Developer"};
    private static final String[] DESCRIPTIONS = {"Performed daily tasks related to database management and " +
            "maintenance.", "Collaborated with team members to develop and execute marketing campaigns.", "Conducted "
            + "market research to inform product development decisions.",
            "Provided customer support through email, " + "phone, and chat channels.", "Managed social media " +
            "accounts" + " and created engaging content for various " + "platforms.",
            "Assisted in the design and " + "development of a" + " new mobile app.", "Handled accounts payable " +
            "and receivable for a small " + "business.", "Generated " + "weekly reports on sales performance and " +
            "presented " + "findings to " + "management.",
            "Provided technical " + "support to clients experiencing " + "issues with software " + "products.",
            "Created and delivered " + "presentations on new products to " + "internal and external " + "stakeholders"
                    + "."};
    private static final String[] DATES = {"2014-10", "2016-01", "2015-05", "2017-08", "2018-02", "2020-06", "2019-11"
            , "2022-01", "2012-09", "2014-12", "2017-03", "2019-06", "2020-10", "2022-12", "2011-06", "2013-09",
            "2016-01", "2018-04", "2013-12", "2016-02", "2015-03", "2016-07", "2012-11", "2014-06", "2019-09",
            "2020" + "-12", "2017-01", "2018-05", "2013-08", "2014-12", "2020-03", "2021-08", "2016-09", "2017-12",
            "2018-06", "2019-11", "2011-07", "2013-02", "2014-04", "2015-10", "2014-10", "2016-01", "2018-05", "2019" +
            "-09", "2022-02", "2023-04", "2015-06", "2017-12", "2019-08", "2020-11", "2016-03", "2018-08", "2020-01",
            "2022-07", "2015-09", "2017-11", "2019-05", "2021-08", "2016-02", "2018-06"};
    private static int datePointer = 0;
    private static final String[] COMPANY_NAMES = {"Green Mountain Coffee Roasters", "Fresh Choice", "Peachtree " +
            "Network", "The Gadget Store", "Mountain View Group", "Cascade Industries", "Sapphire Enterprises",
            "Golden Valley Electric Association", "Pacific Coast Creative", "Meadowbrook Farms", "Oceanic Services",
            "Morning Glory Bakery", "Summit Consulting Group", "Redwood Energy Solutions", "Blue Ridge Furniture",
            "Pioneer Marketing", "Cedar Creek Construction", "Rocky Mountain Productions", "Island Breeze " +
            "Hospitality", "Horizon Global"};
    private static final String[] COMPANY_WEBSITES = {"greenmountaincoffeeroasters.com", "freshchoice.com",
            "peachtreenetwork.com", "thegadgetstore.com", "mountainviewgroup.com", "cascadeindustries.com",
            "sapphireenterprises.com", "gvea.com", "pacificcoastcreative.com", "meadowbrookfarms.com",
            "oceanicservices.com", "morningglorybakery.com", "summitconsultinggroup.com", "redwoodenergysolutions" +
            ".com", "blueridgefurniture.com", "pioneermarketing.com", "cedarcreekconstruction.com",
            "rockymountainproductions.com", "islandbreezehospitality.com", "horizonglobal.com"};
    private static int counter = 0;

    public static Resume createResume(String uuid, String fullName) {
        if (counter == 10) {
            counter = 0;
            datePointer = 0;
        }
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE_NUMBER, PHONE_NUMBERS[counter]);
        resume.addContact(ContactType.EMAIL, generateEmail(fullName));
        resume.addContact(ContactType.GITHUB, generateLink(fullName, "https://github.com/"));
        resume.addContact(ContactType.LINKEDIN, generateLink(fullName, "https://www.linkedin.com/in/"));
        resume.addContact(ContactType.SKYPE, generateSkype(fullName));

        resume.addSection(SectionType.POSITION, new TextSection(POSITIONS[counter]));
        resume.addSection(SectionType.PERSONAL, new TextSection(PERSONALS[counter]));
        resume.addSection(SectionType.ACHIEVEMENTS, new ListSection(getComposedList(ACHIEVEMENTS)));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(getComposedList(QUALIFICATIONS)));
        resume.addSection(SectionType.EXPERIENCE, new CompanySection(getCompaniesList()));
        counter++;
        return resume;
    }

    public static String generateEmail(String fullName) {
        fullName = fullName.replaceAll(" ", "").toLowerCase();
        return fullName + "@" + EMAILS[counter] + ".com";
    }

    public static String generateLink(String fullName, String websiteUrl) {
        fullName = fullName.replaceAll(" ", "").toLowerCase();
        return websiteUrl + fullName;
    }

    public static String generateSkype(String fullName) {
        fullName = fullName.replaceAll(" ", ".").toLowerCase();
        return fullName;
    }

    public static List<String> getComposedList(String[] list) {
        int listPointer = counter * 3;
        return new ArrayList<>(Arrays.asList(list).subList(listPointer, listPointer + 3));
    }

    public static List<Company> getCompaniesList() {
        List<Company> companies = new ArrayList<>();
        List<Period> firstPeriodsPair = getTwoPeriods();
        companies.add(new Company(COMPANY_NAMES[counter * 2], COMPANY_WEBSITES[counter * 2], firstPeriodsPair));
        List<Period> secondPeriodsPair = getTwoPeriods();
        companies.add(new Company(COMPANY_NAMES[counter * 2 + 1], COMPANY_WEBSITES[counter * 2 + 1], secondPeriodsPair));
        return companies;
    }

    private static List<Period> getTwoPeriods() {
        List<Period> periods = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            YearMonth start = YearMonth.parse(DATES[datePointer++]);
            YearMonth end = YearMonth.parse(DATES[datePointer++]);
            periods.add(new Period(TITLES[counter + i], DESCRIPTIONS[counter + i], DateUtil.of(start.getYear(),
                    start.getMonth()), DateUtil.of(end.getYear(), end.getMonth())));
        }
        return periods;
    }

    public static void main(String[] args) {
        final String UUID_1 = "uuid1";
        final String UUID_2 = "uuid2";
        final String UUID_3 = "uuid3";
        final String UUID_4 = "uuid4";
        final String FULL_NAME_1 = "John Smith";
        final String FULL_NAME_2 = "Emily Davis";
        final String FULL_NAME_3 = "Robert Johnson";
        final String FULL_NAME_4 = "Alice Wilson";
        System.out.println(createResume(UUID_1, FULL_NAME_1));
        System.out.println(createResume(UUID_2, FULL_NAME_2));
        System.out.println(createResume(UUID_3, FULL_NAME_3));
        System.out.println(createResume(UUID_4, FULL_NAME_4));
    }
}

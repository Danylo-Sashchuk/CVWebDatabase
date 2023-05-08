package com.basejava.storage;

import java.util.Arrays;
import java.util.Iterator;

public class MockingData {
    //10 numbers
    public final String[] PHONE_NUMBERS = {"(555) 123-4567", "(555) 987-6543", "(555) 555-1212", "(555) " + "456-7890"
            , "(555) 777-8888", "(555) 999-0000", "(555) 222-3333", "(555) 444-5555", "(555) 888-9999", "(555) " +
            "111" + "-2222"};
    //10 mails
    public final String[] EMAILS = new String[]{"gmail", "yahoo", "hotmail", "outlook", "icloud", "protonmail", "aol"
            , "inbox", "fastmail.com", "hushmail.com"};
    //10 positions
    public final String[] POSITIONS = new String[]{"Serving as a project manager for a team of 10.",
            "Working" + " " + "as" + " a software developer for a fast-growing tech startup.", "Holding a leadership "
            + "position in a " + "non" + "-profit organization.",
            "Employed as a marketing specialist for a Fortune " + "500" + " company.",
            "Serving as " + "a customer " + "service representative for a multinational corporation.", "Working as a "
            + "sales" + " executive " + "for" + " a" + " leading e-commerce platform.", "Holding a " + "teaching " +
            "position at a " + "local " + "university.", "Employed " + "as a " + "human resources " + "manager for a "
            + "global " + "organization" + ".", "Serving as a financial " + "analyst for a " + "major " + "investment"
            + " " + "firm.", "Working " + "as a " + "research assistant for a renowned " + "academic institution."};
    //10 personals
    public final String[] PERSONALS =
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
    //30 achievements - 3 per resume
    public final String[] ACHIEVEMENTS =
            new String[]{"Increased sales by 25% over a six-month period " + "through" + " the implementation of " +
                    "targeted marketing campaigns", "Recognized as top-performing " + "sales " + "representative" +
                    " for " + "two consecutive quarters", "Developed " + "and executed " + "successful customer " +
                    "retention" + " " + "strategies " + "resulting " + "in" + " a 20% " + "increase in " + "repeat " + "business", "Designed and " + "launched a new " + "website" + " " + "resulting in a 30%" + " " + "increase in online " + "traffic", "Streamlined " + "internal processes " + "resulting in a 15% " + "reduction" + " in " + "project " + "completion time", "Received" + " company-wide " + "recognition for outstanding project " + "management skills", "Implemented a new " + "inventory " + "management system resulting in a 50% " + "reduction in " + "inventory " + "discrepancies", "Developed" + " " + "and delivered effective employee " + "training" + " programs resulting in " + "improved " + "customer satisfaction" + " scores", "Achieved a " + "perfect " + "safety record by " + "developing and " + "enforcing strict safety " + "protocols", "Led a team " + "of five " + "software developers to " + "deliver a new software " + "product ahead of " + "schedule and " + "under " + "budget", "Designed and " + "developed a new mobile app that " + "received" + " over 10,000 " + "downloads " + "within the first " + "month of release", "Implemented new project management " + "processes " + "that resulted in a 25% " + "increase in " + "project " + "completion rates", "Successfully negotiated" + " " + "and " + "closed " + "contracts with several new key " + "clients " + "resulting in a 40% " + "increase in annual " + "revenue", "Developed and implemented a " + "cost-saving " + "initiative that" + " " + "resulted in a " + "15% " + "reduction in overall " + "expenses", "Received multiple awards " + "for " + "outstanding " + "customer " + "service skills and " + "high " + "levels of client satisfaction", "Developed " + "and " + "launched a new social media " + "campaign resulting in a " + "50% increase in" + " engagement " + "and a 20% " + "increase in website " + "traffic", "Coordinated " + "and executed " + "successful " + "fundraising events " + "resulting in over " + "$100,000 in donations", "Implemented a " + "new " + "employee" + " wellness program " + "resulting in " + "improved employee morale and decreased" + " " + "absenteeism", "Received " + "multiple awards for " + "outstanding performance and high levels " + "of customer " + "satisfaction", "Consistently " + "met" + " and " + "exceeded sales targets resulting" + " in a 30% increase in annual revenue", "Developed and " + "delivered " + "training programs " + "that" + " resulted in improved " + "employee " + "productivity and " + "reduced turnover " + "rates", "Implemented new QA processes that resulted " + "in a 50% reduction " + "in " + "defects and improved " + "product " + "quality", "Developed and executed" + " successful marketing " + "campaigns " + "resulting in a 25% increase in " + "brand " + "awareness", "Successfully launched a new " + "product " + "line " + "resulting in a 40% increase in sales", "Led " + "cross-functional " + "teams to " + "successfully deliver " + "multiple complex projects on time and " + "within budget", "Developed and " + "executed a cost-saving " + "initiative resulting in a 20% reduction " + "in " + "overall expenses", "Received " + "multiple " + "awards for " + "outstanding leadership " + "skills and ability to " + "drive results", "Developed " + "and delivered " + "training " + "programs " + "that " + "resulted in improved employee " + "skills and " + "competencies", "Received multiple awards" + " for " + "outstanding innovation " + "and creativity in " + "product " + "development", "Successfully " + "managed and " + "delivered " + "a " + "large-scale project resulting in a 15% " + "increase in " + "customer satisfaction"};
    //30 qualifications - 3 per resume
    public final String[] QUALIFICATIONS = new String[]{"Excellent communication skills",
            "Effective " + "teamwork" + " and collaboration", "Proven ability to meet deadlines", "Strong " +
            "problem-solving and " + "analytical skills", "Proficient in Microsoft Office Suite",
            "Experience with " + "project management " + "software", "Attention to " + "detail and accuracy", "Strong"
            + " organizational and " + "time management skills", "Ability to adapt to changing " + "priorities",
            "Experience in customer service" + " and client relations", "Ability to work independently and " + "take "
            + "initiative", "Ability to learn " + "quickly and apply new " + "concepts", "Experience in data " +
            "analysis" + " " + "and " + "interpretation", "Excellent " + "research and writing " + "skills",
            "Proficiency in a " + "second " + "language", "Ability " + "to work in a " + "fast-paced environment",
            "Experience in public " + "speaking and " + "presentations",
            "Knowledge of " + "industry" + "-specific " + "software", "Ability to think" + " creatively and " +
            "outside the box", "Strong leadership and " + "decision-making skills", "Experience " + "in budget " +
            "management", "Passion for continuous learning and " + "professional development", "Ability to " + "build"
            + " and maintain " + "positive relationships with colleagues and " + "clients", "Expertise in " +
            "problem " + "identification " + "and " + "resolution", "Experience in sales and marketing",
            "Ability " + "to work " + "well under" + " " + "pressure", "Strong customer service skills", "Proficient "
            + "in various programming languages including Java, Python, and C++", "Extensive experience with project "
            + "management tools such as JIRA and Trello",
            "Excellent communication skills, both written and verbal, " + "with a proven ability to work " +
                    "collaboratively " + "in a team environment"};
    //20 work titles for company experience - 4 per person, (2 companies with 2 periods in each)
    public final String[] WORK_TITLES = {"Marketing Manager", "Software Engineer", "Operations Coordinator",
            "Sales " + "Associate", "Graphic Designer", "Financial Analyst", "Human Resources Specialist",
            "Product " + "Manager", "Customer Service Representative", "Web Developer", "Marketing Coordinator",
            "Human Resources Manager", "Project Manager", "Sales Representative", "Data Scientist",
            "Social Media " + "Specialist", "Social Media Manager", "HR Generalist", "Project Coordinator", "Content "
            + "Writer", "Sales Associate", "IT Support Specialist", "Digital Marketing Manager", "Software Developer"
            , "Administrative Assistant", "Business Analyst", "Public Relations Specialist", "Account Manager",
            "Customer Success Manager", "Supply Chain Analyst", "UX/UI Designer", "Operations Manager",
            "Financial " + "Controller", "Event Coordinator", "Marketing Analyst", "HR Coordinator", "Data Analyst",
            "Content " + "Marketing Manager", "Business Development Manager", "Sales Manager",
            "Quality Assurance " + "Tester"};
    //20 work descriptions for company experience - 4 per person, (2 companies with 2 periods in each)
    public final String[] WORK_DESCRIPTIONS = {"Performed daily tasks related to database management and " +
            "maintenance" + ".", "Collaborated with team members to develop and execute marketing campaigns.",
            "Conducted " + "market" + " research to inform product development decisions.", "Provided customer " +
            "support through email, " + "phone, and chat channels.",
            "Managed social media " + "accounts" + " and " + "created engaging content for " + "various " +
                    "platforms.", "Assisted in the design and " + "development" + " of a" + " new mobile app.",
            "Handled accounts payable " + "and receivable for a small " + "business.", "Generated " + "weekly " +
            "reports" + " on sales performance and " + "presented " + "findings to " + "management.", "Provided " +
            "technical " + "support to clients experiencing " + "issues with software" + " " + "products.", "Created "
            + "and delivered " + "presentations on new products to " + "internal and " + "external " + "stakeholders" + ".", "Coordinated with cross-functional teams to ensure timely delivery of projects", "Developed and implemented effective marketing strategies to increase brand awareness", "Provided exceptional customer service, resolving issues and concerns promptly", "Managed and trained a team of sales representatives to exceed sales goals", "Created engaging content for social media platforms to increase engagement and followers", "Performed data analysis and developed actionable insights to improve business performance", "Maintained accurate records and financial statements for the organization", "Conducted research and developed reports on market trends and competitor analysis", "Designed and developed user-friendly interfaces for web and mobile applications", "Collaborated with clients to understand their needs and provide customized solutions", "Project Manager for software development team", "Marketing Analyst for a large corporation", "Human Resources Assistant at a startup company", "IT Support Specialist for a healthcare organization", "Social Media Manager for a fashion brand", "Financial Analyst at an investment firm", "Software Engineer at a tech startup", "Graphic Designer for a web design agency", "Sales Representative for a software company", "Customer Service Representative at a retail store", "Research Scientist at a pharmaceutical company", "Administrative Assistant at a law firm", "Operations Manager for a logistics company", "Content Writer for a digital marketing agency", "Data Analyst at a financial institution", "Event Coordinator for a nonprofit organization", "Web Developer for an e-commerce business", "Art Director for an advertising agency", "Business Development Manager for a consulting firm", "Product Manager for a consumer electronics company"};
    //160 dates - every person have 16 dates
    public final String[] DATES = {"2014-10", "2016-01", "2015-05", "2017-08", "2018-02", "2020-06", "2019-11", "2022"
            + "-01", "2012-09", "2014-12", "2017-03", "2019-06", "2020-10", "2022-12", "2011-06", "2013-09", "2016-01"
            , "2018-04", "2013-12", "2016-02", "2015-03", "2016-07", "2012-11", "2014-06", "2019-09", "2020" + "-12",
            "2017-01", "2018-05", "2013-08", "2014-12", "2020-03", "2021-08", "2016-09", "2017-12", "2018-06",
            "2019" + "-11", "2011-07", "2013-02", "2014-04", "2015-10", "2014-10", "2016-01", "2018-05", "2019" +
            "-09", "2022" + "-02", "2023-04", "2015-06", "2017-12", "2019-08", "2020-11", "2016-03", "2018" + "-08",
            "2020" + "-01", "2022-07", "2015-09", "2017-11", "2019-05", "2021-08", "2016-02", "2018" + "-06",
            "2014" + "-10", "2016-01", "2008-02", "2009-05", "2013-06", "2015-11", "2010-09", "2012-12", "2005-04",
            "2007-07", "2009-11", "2011-02", "2012-03", "2014-06", "2015-08", "2017-11", "2006-01", "2007" + "-04",
            "2018-06", "2020-09", "2013-01", "2014-04", "2011-07", "2013-10", "2004-10", "2006-01", "2019-04", "2021" +
            "-07", "2015" + "-01", "2016-04", "2017-10", "2019-01", "2008-05", "2009-08", "2014-08", "2016" + "-11",
            "2010" + "-03", "2012-06", "2007-02", "2008-05", "2012-10", "2014-01", "2016-05", "2018" + "-08",
            "2009" + "-12", "2011" + "-03", "2005-09", "2007-12", "2013-04", "2015-07", "2011-10", "2013" + "-01",
            "2006" + "-07", "2007-10", "2018-02", "2019" + "-05", "2014-05", "2016-08", "2010-08", "2012-11", "2008" +
            "-03", "2009" + "-06", "2015" + "-02", "2016-05", "2007-11", "2009-02", "2012-04", "2014-07", "2016-09",
            "2018-12", "2013-02", "2014-05", "2005" + "-05", "2006-08", "2011" + "-08", "2013-11", "2019" + "-01",
            "2020" + "-04", "2014-02", "2015" + "-05", "2009-09", "2010" + "-12", "2006-04", "2007-07", "2017" + "-01"
            , "2018-04", "2010-04", "2012-07", "2008" + "-06", "2009-09", "2015-03", "2016-06", "2012" + "-11", "2013"
            + "-02", "2019-07", "2020-12", "2017-05", "2018-11", "2022-01", "2023-06"};
    //20 company names and websites
    public final String[] COMPANY_WEBSITES = {"greenmountaincoffeeroasters.com", "freshchoice.com", "peachtreenetwork"
            + ".com", "thegadgetstore.com", "mountainviewgroup.com", "cascadeindustries.com",
            "sapphireenterprises" + ".com", "gvea.com", "pacificcoastcreative.com", "meadowbrookfarms.com",
            "oceanicservices.com", "morningglorybakery.com", "summitconsultinggroup.com", "redwoodenergysolutions" +
            ".com", "blueridgefurniture.com", "pioneermarketing.com", "cedarcreekconstruction.com",
            "rockymountainproductions" + ".com", "islandbreezehospitality.com", "horizonglobal.com"};
    public final String[] COMPANY_NAMES = {"Green Mountain Coffee Roasters", "Fresh Choice", "Peachtree " + "Network"
            , "The Gadget Store", "Mountain View Group", "Cascade Industries", "Sapphire Enterprises", "Golden " +
            "Valley" + " Electric Association", "Pacific Coast Creative", "Meadowbrook Farms", "Oceanic " + "Services"
            , "Morning " + "Glory Bakery", "Summit Consulting Group", "Redwood Energy " + "Solutions", "Blue " +
            "Ridge " + "Furniture", "Pioneer " + "Marketing", "Cedar Creek Construction", "Rocky Mountain " +
            "Productions", "Island " + "Breeze " + "Hospitality", "Horizon Global"};
    //20 educational names and websites
    public final String[] EDUCATION_NAMES = {"Harvard University", "Stanford University", "Massachusetts " +
            "Institute of Technology", "University of Oxford", "University of Cambridge",
            "California Institute of " + "Technology", "Princeton University", "University of Chicago", "Yale " +
            "University", "Columbia University", "University of Pennsylvania", "Imperial College London", "ETH " +
            "Zurich", "University of California, " + "Berkeley", "University of Michigan", "University of Tokyo",
            "National University of Singapore", "Peking " + "University", "University of Melbourne",
            "University of " + "Toronto"};
    public final String[] EDUCATION_WEBSITES = {"harvarduniversity.edu", "stanforduniv.edu", "mit.edu",
            "oxforduniv" + ".edu", "cambridgeuniv.edu", "caltech.edu", "princetonuniv.edu", "uchicago.edu", "yaleuniv" +
            ".edu", "columbiauniv.edu", "upenn.edu", "imperialcollege.edu", "ethzurich.edu", "ucberkeley.edu", "umich" +
            ".edu", "utokyo.edu", "nus.edu", "pekinguniv.edu", "unimelb.edu", "uoft.edu"};
    //40 educational titles and descriptions, because every person has 1 education section with 2 periods
    public final String[] EDUCATION_TITLE = {"Bachelor's degree in Computer Science", "Master in Computer " +
            "Science", "Bachelor's degree in Psychology", "Master in Counseling", "Bachelor's degree in Biology",
            "Master in Medical Science", "Bachelor's degree in Business Administration", "Master in Finance",
            "Bachelor's degree in Education", "Master in Curriculum and Instruction", "Bachelor's degree in " +
            "Communications", "Master in Public Relations", "Bachelor's degree in Engineering", "Master in " +
            "Mechanical" + " Engineering", "Bachelor's degree in History", "Master in Cultural Studies", "Bachelor's "
            + "degree in " + "English", "Master in Creative Writing", "Bachelor's degree in Environmental Science",
            "Master in " + "Sustainability", "Bachelor's degree in Political Science", "Master in International " +
            "Relations", "Bachelor's degree in Economics", "Master in Applied Economics", "Bachelor's degree in " +
            "Marketing", "Master in Digital Marketing", "Bachelor's degree in Graphic Design", "Master in Fine Arts",
            "Bachelor's " + "degree in Sociology", "Master in Social Work", "Bachelor's degree in Mathematics",
            "Master in " + "Statistics", "Bachelor's degree in Nutrition", "Master in Dietetics", "Bachelor's degree "
            + "in Criminal " + "Justice", "Master in Criminal Justice", "Bachelor's degree in Public Health", "Master"
            + " " + "in Health " + "Administration", "Bachelor's degree in Music", "Master in Music Education"};
    public final String[] EDUCATION_DESCRIPTION = {"Faculty of Computer Science", "Faculty of Computer " + "Science",
            "Faculty of Social Sciences or Faculty of Arts and Humanities", "Faculty of Education or " + "Faculty of "
            + "Social Work", "Faculty of Science", "Faculty of Medicine or Faculty of Health Sciences",
            "Faculty of " + "Business or School of Management", "Faculty of Business or School of Management",
            "Faculty " + "of" + " " + "Education", "Faculty of Education",
            "Faculty of Communications or School of " + "Journalism", "Faculty " + "of " + "Communications or School " +
            "of Journalism", "Faculty of Engineering or " + "School" + " of " + "Engineering", "Faculty of " +
            "Engineering or School of Engineering", "Faculty of " + "Arts and " + "Humanities or" + " Department of " +
            "History", "Faculty of Arts and " + "Humanities" + " or Department " + "of Cultural " + "Studies",
            "Faculty of" + " Arts and Humanities or " + "Department " + "of English", "Faculty" + " of Arts and " +
            "Humanities or Department of " + "Creative Writing", "Faculty" + " of " + "Science or School" + " of " +
            "Environmental " + "Studies", "Faculty of " + "Environmental Studies " + "or School of " +
            "Sustainability", "Faculty of Social " + "Sciences or Department of " + "Political " + "Science",
            "Faculty of Social " + "Sciences or Department of " + "International Relations", "Faculty" + " of Social "
            + "Sciences or Department " + "of " + "Economics",
            "Faculty of" + " Social Sciences or " + "Department " + "of" + " " + "Economics", "Faculty of " +
            "Business or " + "School of " + "Marketing", "Faculty of " + "Business" + " or School of " + "Marketing",
            "Faculty of " + "Fine" + " Arts or School of Design", "Faculty " + "of" + " Fine Arts or School of " +
            "Design", "Faculty of Social " + "Sciences or Department of " + "Sociology", "Faculty " + "of " + "Social"
            + " Work or " + "Department of Social " + "Work", "Faculty of " + "Science or " + "Department of " +
            "Mathematics", "Faculty of " + "Science or Department of " + "Statistics", "Faculty of" + " Health " +
            "Sciences or " + "Department of " + "Nutrition",
            "Faculty of Health Sciences or " + "Department " + "of " + "Dietetics", "Faculty of" + " Social " +
            "Sciences or" + " School of " + "Criminal Justice", "Faculty" + " of " + "Social Sciences or " + "School "
            + "of Criminal Justice", "Faculty of Health " + "Sciences or " + "School of " + "Public " + "Health",
            "Faculty " + "of Health Sciences or School " + "of " + "Health " + "Administration", "Faculty of " +
            "Music or " + "Department " + "of Music", "Faculty of Education " + "or" + " " + "Department of Music"};
    private static final MockingData instance = new MockingData();

    private MockingData() {
    }

    public static MockingData getInstance() {
        return instance;
    }

    public Iterator<String> getIterator(String[] from) {
        return Arrays.stream(from).iterator();
    }
}

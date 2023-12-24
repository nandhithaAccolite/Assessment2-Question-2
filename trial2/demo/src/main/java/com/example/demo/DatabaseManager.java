package com.example.demo;
import org.apache.commons.dbcp2.BasicDataSource;
import org.jfree.data.general.DefaultPieDataset;
import java.sql.*;
import java.util.List;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "LINlin@2002";
    private static final String TABLE_NAME = "Data"; // Table name to be created

    private static final BasicDataSource dataSource = new BasicDataSource();


    static {
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
    }

    public static void createTable() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "date VARCHAR(255)," +
                    "month VARCHAR(255)," +
                    "team VARCHAR(255)," +
                    "panelName VARCHAR(255)," +
                    "round VARCHAR(255)," +
                    "skill VARCHAR(255)," +
                    "time VARCHAR(255)," +
                    "candidateCurrentLoc VARCHAR(255)," +
                    "candidatePreferredLoc VARCHAR(255)," +
                    "candidateName VARCHAR(255)" +
                    ")";

            statement.executeUpdate(createTableQuery);

            System.out.println("Table " + TABLE_NAME + " created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(List<DataModel> data) {
        data.parallelStream().forEach(record -> {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO " + TABLE_NAME + " (date, month, team, panelName, round, skill, time, " +
                                 "candidateCurrentLoc, candidatePreferredLoc, candidateName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                preparedStatement.setString(1, record.getDate());
                preparedStatement.setString(2, record.getMonth());
                preparedStatement.setString(3, record.getTeam());
                preparedStatement.setString(4, record.getPanelName());
                preparedStatement.setString(5, record.getRound());
                preparedStatement.setString(6, record.getSkill());
                preparedStatement.setString(7, record.getTime());
                preparedStatement.setString(8, record.getCandidateCurrentLocation());
                preparedStatement.setString(9, record.getCandidatePreferredLocation());
                preparedStatement.setString(10, record.getCandidateName());


                // Execute the query
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                //e.printStackTrace();
            } catch (Exception e) {
                //e.printStackTrace();
            }
        });

    }

    public static void teamWithTheMaxInterviews() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Query to find the team with the maximum number of interviews for October and November 2023
            String query = "SELECT team, COUNT(*) as interviewCount FROM data WHERE month IN ('Oct-23', 'Nov-23') GROUP BY team ORDER BY interviewCount DESC LIMIT 1;";

            ResultSet resultSet = statement.executeQuery(query);

            if (((ResultSet) resultSet).next()) {
                System.out.println("Team with Maximum Interviews: " + resultSet.getString("team"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void teamWithTheMinInterviews() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Query to find the team with the minimum number of interviews for October and November 2023
            String query = "SELECT team, COUNT(*) as interviewCount FROM data WHERE month IN ('Oct-23', 'Nov-23') GROUP BY team ORDER BY interviewCount ASC LIMIT 1;";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                System.out.println("Team with Minimum Interviews: " + resultSet.getString("team"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void theTop3Panels() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Query to find the top 3 panels for the month of October and November 2023 using lambda streams
            String query = "SELECT panelName, COUNT(*) as interviewCount FROM data WHERE month IN ('Oct-23', 'Nov-23') GROUP BY panelName ORDER BY interviewCount DESC LIMIT 3;";

            ResultSet resultSet = statement.executeQuery(query);
            DefaultPieDataset dataset = new DefaultPieDataset();
            while (resultSet.next()) {
                dataset.setValue(resultSet.getString("panelName"), resultSet.getInt("interviewCount"));
                System.out.println("Panel: " + resultSet.getString("panelName") +
                        ", Interview Count: " + resultSet.getInt("interviewCount"));
            }
            PdfGenerator.createPanelPdf(dataset, "C:\\Users\\linga.nandhitha\\Downloads\\trial2\\demo\\src\\main\\java\\output\\Top3Panels.pdf");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void theTop3Skills() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Query to find the top 3 skills for the month of October and November 2023 using a view on the database
            String query = "CREATE OR REPLACE VIEW top_skills_view AS SELECT skill, COUNT(*) as interviewCount FROM data WHERE month IN ('Oct-23', 'Nov-23') GROUP BY skill ORDER BY interviewCount DESC LIMIT 3";
            statement.executeUpdate(query);
            String Query2 = "SELECT * FROM top_skills_view;";
            ResultSet resultSet = statement.executeQuery(Query2);
            DefaultPieDataset dataset = new DefaultPieDataset();
            while (resultSet.next()) {
                dataset.setValue(resultSet.getString("skill"), resultSet.getInt("interviewCount"));
                System.out.println("Skill: " + resultSet.getString("skill") +
                        ", Interview Count: " + resultSet.getInt("interviewCount"));
            }
            PdfGenerator.createSkillsPdf(dataset, "C:\\Users\\linga.nandhitha\\Downloads\\trial2\\demo\\src\\main\\java\\output\\Top3Skills.pdf");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void theSkillsInPeakTime() {
        System.out.println("Top 3 Skills in Peak Time");
        String sqlQuery = "select time,count(*) as InterviewTimeCount from data group by time Order by InterviewTimeCount desc limit 1;";
        String time="";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {

                time = resultSet.getString("time");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        DefaultPieDataset dataset = new DefaultPieDataset();
        System.out.println("Peak Time : "+time);
        String sqlQuery1 = "select skill,count(*) as SkillCount from data where time = '"+time+"' group by skill Order by skillcount desc limit 3;";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(sqlQuery1);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String teamName = resultSet.getString("Skill");
                int interviewCount = resultSet.getInt("SkillCount");
                dataset.setValue(resultSet.getString("skill"), resultSet.getInt("SkillCount"));
                System.out.println("Skill : " + teamName);
                System.out.println("Count: " + interviewCount);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println();
        PdfGenerator.createSkillsInPeakTimePdf(dataset, "C:\\Users\\linga.nandhitha\\Downloads\\trial2\\demo\\src\\main\\java\\output\\skillsInPeakTime.pdf");

    }

    }



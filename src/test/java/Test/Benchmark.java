package Test;

import org.frostyheco.databse.Session;
import org.frostyheco.databse.SessionFactory;
import org.frostyheco.databse.methods.BatchInsertInfo;
import org.frostyheco.databse.methods.CopyInsertInfo;
import org.frostyheco.databse.methods.TransactionInfo;
import org.frostyheco.exception.BuildingException;
import org.frostyheco.exception.OperationException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyResultSet;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Benchmark {
    int i = 0;

    @Test
    public void normalInsert() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/ormc");
        config.setUsername("postgres");
        config.setPassword("1234567");
        config.setDriverClassName("org.postgresql.Driver");
        DataSource dataSource = new HikariDataSource(config);
        Session s = SessionFactory.create(dataSource, "benchmark");
        final int[] cnt = {0};
        s.statement("truncateBT");
        long start = System.currentTimeMillis();

        s.beginTransaction(new TransactionInfo() {
            @Override
            public Object inTransaction() throws OperationException, SQLException {
                try (BufferedReader infile
                             = new BufferedReader(new FileReader("D:\\ComputerScience\\Projects\\DB_Proj2\\MyORM\\sustc-api\\src\\test\\resources\\Test\\tiny.csv"))) {

                    String line;
                    String[] parts;
                    String studentid;
                    String name;


                    while ((line = infile.readLine()) != null) {
                        parts = line.split(",");
                        if (parts.length > 1) {
                            studentid = parts[1];
                            name = parts[2];
                            insert("student", studentid, name);
                            cnt[0]++;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(cnt[0] + " records successfully loaded");
        System.out.println("Loading speed : "
                + (cnt[0] * 1000) / (end - start)
                + " records/s");
    }

    @Test
    public void initialize() throws BuildingException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/ormc");
        config.setUsername("postgres");
        config.setPassword("1234567");
        config.setDriverClassName("org.postgresql.Driver");
        DataSource dataSource = new HikariDataSource(config);
        long times = (int) 1e4;
        long start = System.currentTimeMillis();
        Session s;
        for (int j = 0; j < times; j++) {
            s = SessionFactory.create(dataSource, "test");
        }
        long end = System.currentTimeMillis();
        long misec = end - start;
        System.out.println("misec:" + misec);
        System.out.println("Times per sec:" + (times * 1000.0 / misec));
    }

    @Test
    public void test() throws SQLException {
        HikariConfig config = new HikariConfig();
        //config.setJdbcUrl("jdbc:postgresql://localhost:5432/ormc?user=postgres&password=1234567&reWriteBatchedInserts=true");
//        config.setUsername("postgres");
//        config.setPassword("1234567");
        config.setDriverClassName("org.postgresql.Driver");
        DataSource dataSource = new HikariDataSource(config);
        Connection con = dataSource.getConnection();
        con.setClientInfo("reWriteBatchedInserts", "true");
        con.getClientInfo().setProperty("reWriteBatchedInserts", "true");
        System.out.println(con.getClientInfo());
    }

    @Test
    public void batchInsert() throws Exception {
        HikariConfig config = new HikariConfig();
        //config.setJdbcUrl("jdbc:postgresql://localhost:5432/ormc?user=postgres&password=1234567&reWriteBatchedInserts=true");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/ormc");
        config.setUsername("postgres");
        config.setPassword("1234567");
        //config.addDataSourceProperty("reWriteBatchedInserts",true);
        config.setDriverClassName("org.postgresql.Driver");
        DataSource dataSource = new HikariDataSource(config);

        Properties p = new Properties();
        p.setProperty("reWriteBatchedInserts", "true");
        System.out.println(((HikariDataSource) dataSource).getJdbcUrl());

        Session s = SessionFactory.create(dataSource, "benchmark");
        s.statement("truncateBT");

        List<Student> students = new ArrayList<>();

        try (BufferedReader infile
                     = new BufferedReader(new FileReader("D:\\ComputerScience\\Projects\\DB_Proj2\\MyORM\\sustc-api\\src\\test\\resources\\Test\\tiny.csv"))) {
            String line;
            String[] parts;
            String studentid;
            String name;

            while ((line = infile.readLine()) != null) {
                parts = line.split(",");
                if (parts.length > 1) {
                    studentid = parts[1];
                    name = parts[2];
                    students.add(new Student(studentid, name));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Collections.shuffle(students);
        long start = System.currentTimeMillis();
        long studentTotal = s.batchInsert(students, new BatchInsertInfo<>("student", 2000) {
            @Override
            public void insert(Student student) throws SQLException {
                insert(student.studentid, student.name);
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(studentTotal + " records successfully loaded");
        System.out.println("Loading speed : "
                + (studentTotal * 1000) / (end - start)
                + " records/s");
    }

    @Test
    public void copyInsert() throws Exception {
        HikariConfig config = new HikariConfig();
        //config.setJdbcUrl("jdbc:postgresql://localhost:5432/ormc?user=postgres&password=1234567&reWriteBatchedInserts=true");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/ormc");
        config.setUsername("postgres");
        config.setPassword("1234567");
        config.setDriverClassName("org.postgresql.Driver");
        DataSource dataSource = new HikariDataSource(config);

        Session s = SessionFactory.create(dataSource, "benchmark");
        s.statement("truncateBT");
        //started at here to be 公平
        List<Student> students = new ArrayList<>();
        for (int j = 0; j < 1; j++) {
            try (BufferedReader infile
                         = new BufferedReader(new FileReader("D:\\ComputerScience\\Projects\\DB_Proj2\\MyORM\\sustc-api\\src\\test\\resources\\Test\\tiny.csv"))) {
                String line;
                String[] parts;
                String studentid;
                String name;

                while ((line = infile.readLine()) != null) {
                    parts = line.split(",");
                    if (parts.length > 1) {
                        studentid = parts[1];
                        name = parts[2];
                        students.add(new Student(studentid, name+i));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Collections.shuffle(students);
        long start = System.currentTimeMillis();
        long studentTotal = s.copyInsert(students, new CopyInsertInfo<>("benchmarktest") {
            @Override
            public void insert(Student object) throws IOException {
                insert(object.studentid, object.name);
            }
        });

        long end = System.currentTimeMillis();
        System.out.println(studentTotal + " records successfully loaded");
        System.out.println("Loading speed : "
                + (studentTotal * 1000) / (end - start)
                + " records/s");
    }
}

class Student {
    String studentid;
    String name;

    public Student(String studentid, String name) {
        this.studentid = studentid;
        this.name = name;
    }
}
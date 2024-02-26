package Test;

import org.frostyheco.databse.SessionFactory;
import org.frostyheco.exception.BuildingException;
import utils.TestDataSources;
import org.frostyheco.databse.Session;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class DatabaseTest {
    Session db;
    private void connect(String name) throws BuildingException {
        try {
            TestDataSources t = new TestDataSources();
            t.getConnection();
            db = SessionFactory.create(t, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void xmlTest() throws Exception {
        connect("test");
        assert db.getSegment("seg1").equals("hi");
    }

    @Test
    public void queryTest1() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        Object o = db.query("complexObj");
        ComplexObj c = (ComplexObj) o;
        assert c.id == 1;
        assert c.name.equals("龙志");
        assert c.type.equals(Type.type2);
    }

    @Test
    public void transaction() throws Exception {

    }

    @Test
    public void queryTest2() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        db.insert("complexObj", 2, "龙志2", 1);
        db.insert("complexObj", 3, "飞", 3);
        Object temp = db.query("complexObj1.1");
        ComplexObj[] obj = (ComplexObj[]) temp;
        assert obj[0].id == 1;
        assert obj[1].name.equals("龙志2");
        assert obj[2].type.equals(Type.type3);
    }

    @Test
    public void queryTest3() throws Exception {
        connect("test");
        db.statement("truncateTest2");
        db.insert("complexObj2", 1, "欢迎龙志！");
        Object temp = db.query("complexObj2");
        ComplexObj2 obj = (ComplexObj2) temp;
        assert obj.id == 1;
        assert obj.testCase.equals("欢迎龙志！");
    }

    @Test
    public void queryTest4() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        db.insert("complexObj", 2, "龙志2", 1);
        db.insert("complexObj", 3, "飞", 3);
        Object temp = db.query("complexObj3");
        ArrayList<Integer> list = (ArrayList<Integer>) temp;
        assert list.size() == 3;
    }

    @Test
    public void queryTest5() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        db.insert("complexObj", 2, "龙志2", 1);
        db.insert("complexObj", 3, "飞", 3);
        Object temp = db.query("complexObj4");
        int[] list = (int[]) temp;
        assert list.length == 3;
    }

    @Test
    public void queryTest6() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        Object temp = db.query("complexObj5");
        int i = (int) temp;
        assert i == 1;
    }

    @Test
    public void queryTest7() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        db.insert("complexObj", 2, "龙志2", 1);
        db.insert("complexObj", 3, "飞", 3);
        Object temp = db.query("complexObj6");
        Integer[] list = (Integer[]) temp;
        assert list.length == 3;
    }

    @Test
    public void queryTest8() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        db.insert("complexObj", 2, "龙志2", 1);
        db.insert("complexObj", 3, "飞", 3);
        Object temp = db.query("complexObj7");
        assert ((Integer) temp) == null;
    }

    @Test
    public void queryTest9() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        Object temp = db.query("complexObj8");
        ComplexObj obj = (ComplexObj) temp;
        assert obj.id == 1;
        assert obj.name.equals("龙志");
        assert obj.type.equals(Type.type2);
    }

    @Test
    public void queryTest10() throws Exception {
        connect("test");
        db.statement("truncateTest2");
        Object temp = db.query("complexObj2");
        ComplexObj2 obj = (ComplexObj2) temp;
        assert obj == null;
    }
    //TODO 测试map全为null，部分字段为null,query结果为null的情况

    @Test
    public void queryTest11() throws Exception {
        connect("test");
        db.statement("truncateTest3");
        Timestamp ts = new Timestamp(103, 1, 1, 22, 33, 33, 0);
        db.insert("test3", 1, "龙志", "test2", ts);
        db.insert("test3.2", 1, 1);
        db.insert("test3.2", 1, 2);
        db.insert("test3.2", 1, 3);
        db.insert("test3.2", 2, 1);
        db.insert("test3.2", 2, 7);
        Object temp = db.query("complexObj9", 1);
        ComplexObj3 obj = (ComplexObj3) temp;
        assert obj.id == 1;
        assert obj.name.equals("龙志");
        assert obj.test2.equals("test2");
        assert obj.fei.equals(ts);
        assert Arrays.equals(obj.array, new int[]{1, 2, 3});
    }

    @Test
    public void queryTest12() throws Exception {
        connect("test");
        db.statement("truncateTest3");
        Object temp = db.query("complexObj9", 1);
        ComplexObj3 obj = (ComplexObj3) temp;
        assert obj == null;
    }

    @Test
    public void queryTest13() throws Exception {
        connect("test");
        db.statement("truncateTest3");
        db.insert("test3", 1, "龙志", "test2", null);
        Object temp = db.query("complexObj9", 1);
        ComplexObj3 obj = (ComplexObj3) temp;
        assert obj.id == 1;
        assert obj.name.equals("龙志");
        assert obj.test2.equals("test2");
        assert obj.fei == null;
        assert obj.array == null;
    }

    @Test
    public void queryTest14() throws Exception {
        connect("test");
        db.statement("truncateTest3");
        db.insert("test3", 1, "龙志", "test2", null);
        db.insert("test3.2", 2, 1);
        Object temp = db.query("complexObj9", 1);
        ComplexObj3 obj = (ComplexObj3) temp;
        assert obj.id == 1;
        assert obj.name.equals("龙志");
        assert obj.test2.equals("test2");
        assert obj.fei == null;
        assert obj.array == null;
    }

    @Test
    public void queryTest15() throws Exception {
        connect("test");
        db.statement("truncateTest");
        db.insert("complexObj", 1, "龙志", 2);
        db.insert("complexObj", 2, "龙志2", 1);
        db.insert("complexObj", 3, "飞", 3);
        Object temp = db.query("complexObj10");
        Set<Integer> list = (Set<Integer>) temp;
        assert list.size() == 3;
    }

    @Test
    public void queryTest16() throws Exception {
        connect("test");
        db.statement("truncateTest");
        Object temp = db.query("complexObj10");
        Set<Integer> list = (Set<Integer>) temp;
        assert list.size() == 0;
    }

    @Test
    public void queryTest17() throws Exception {
        connect("test");
        db.statement("truncateReal");
        db.insert("userInfo", 1, "龙志", "男", null, 1, 100, "我是飞！", 123456, null, null, 0);
        db.insert("userInfo", 2, "飞", "女", null, 1, 50, "我是龙志！", 123456, null, null, 0);
        db.insert("follow", 1, 2);
        Object temp = db.query("real", 1);
        UserInfoResp obj = (UserInfoResp) temp;
        assert obj.getMid() == 1;
    }

    @Test
    public void test114() throws Exception {
        connect("test");
        String sql = db.getInsert("follow");
        db.executeVoidSQL("""
                                drop table if exists longzhi;
                                create table longzhi
                                (
                                id int
                                );
                """);
        db.delete("follow", 1, 2);
        int res = db.executeDMLSQL(sql, 1, 2);
        assert res == 1;
    }
}

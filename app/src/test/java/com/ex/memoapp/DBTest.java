package com.ex.memoapp;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.List;

import com.ex.memoapp.db.MemoDAO;
import com.ex.memoapp.vo.MemoVO;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBTest {
    private MemoDAO dao;

    @Before
    public void setup() {
        ShadowLog.stream = System.out;
        dao = new MemoDAO(RuntimeEnvironment.application);
    }

    @After
    public void tearDown() {
        dao.close();
    }

    @Test
    public void testDB_1_Insertion() {
        for(int i=0;i<10;i++) {
            MemoVO vo = new MemoVO();
            vo.setTitle("제목"+i);
            vo.setContent("내용내용내용"+i);
            dao.insert(vo);
        }
    }

    @Test
    public void testDB_2_SelectionAll() {
        List<MemoVO> list = dao.getAll();
        for(MemoVO vo:list) {
            System.out.println(vo.toString());
        }
    }

    @Test
    public void testDB_3_Selection() {
        MemoVO vo = dao.getOne(1);
        System.out.println(vo.toString());
    }

    @Test
    public void testDB_4_Update() {
        testDB_1_Insertion();

        MemoVO vo = dao.getOne(3);
        vo.setContent("내용바꼈음");
        boolean result = dao.update(vo);
        assertEquals(true,result);

        testDB_2_SelectionAll();
    }

    @Test
    public void testDB_5_Delete() {
        testDB_1_Insertion();

        boolean result = dao.delete(10);
        assertEquals(true, result);

        testDB_2_SelectionAll();
    }
}

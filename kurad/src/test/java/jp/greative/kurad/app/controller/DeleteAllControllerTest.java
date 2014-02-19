package jp.greative.kurad.app.controller;

import org.junit.Test;
import static org.junit.Assert.*;
import org.hamcrest.core.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * DESCRIPTION
 *
 * @author harakazuhiro
 * @since 2013/10/18 23:09
 */
public class DeleteAllControllerTest {
    //@Test
    public void testDeleteDirectory() throws Exception {
        File dir = new File("kurad/src/test/resources/tmptest");
        if(!dir.exists()) {
            dir.mkdir();
        }
        File file = new File("kurad/src/test/resources/tmptest/hoge.txt");
        Boolean isFile = file.createNewFile();
        System.out.println("isFile -> " + isFile);
        List<String> result = DeleteAllController.deleteDirectory("kurad/src/test/resources/tmptest");
        System.out.println(result);
    }

    @Test
    public void testPathSeparatorEndWith001() throws Exception {
        String result = DeleteAllController.pathSeparatorEndWith("/foo/bar");
        assertThat(result, IsEqual.equalTo("/foo/bar"));
    }

    @Test
    public void testPathSeparatorEndWith002() throws Exception {
        String result = DeleteAllController.pathSeparatorEndWith("/foo/bar/");
        assertThat(result, IsEqual.equalTo("/foo/bar"));
    }

    @Test
    public void testPathSeparatorEndWith003() throws Exception {
        String result = DeleteAllController.pathSeparatorEndWith("/foo/bar//");
        assertThat(result, IsEqual.equalTo("/foo/bar"));
    }

    @Test
    public void testDeleteDirectoryOrFile() throws Exception {

    }
}

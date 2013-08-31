package jp.greative.kurad.app.controller;

import jp.greative.kurad.Colors;
import jp.greative.kurad.app.model.result.CrudResult;
import jp.greative.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Template controller
 */
public class TemplateController {

    /**
     * execute method
     * @return
     */
    public static String apply() {

        List<String> fileList = new ArrayList<String>(){{
            add("conf/kurad.conf");
            add("dateUtil.mustache");
            add("defaultController.mustache");
            add("defaultController_Admin.mustache");
            add("defaultCreate.mustache");
            add("defaultDelete.mustache");
            add("defaultIndex.mustache");
            add("defaultModel_Admin.mustache");
            add("defaultModel.mustache");
            add("defaultModelAdmin.mustache");
            add("defaultSearch.mustache");
            add("defaultSecured.mustache");
            add("defaultTopController.mustache");
            add("defaultUpdate.mustache");
            add("defaultView.mustache");
            add("defaultViewCreate_Admin.mustache");
            add("defaultViewCreate.mustache");
            add("defaultViewDetail.mustache");
            add("defaultViewIndex.mustache");
            add("defaultViewLogin.mustache");
            add("defaultViewMain.mustache");
            add("defaultViewSearch.mustache");
            add("defaultViewTop.mustache");
            add("defaultViewUpdate_Admin.mustache");
            add("defaultViewUpdate.mustache");
            add("inputNormal.mustache");
            add("inputTable.mustache");
            add("inputTableWithManyToMany.mustache");
            add("inputTableWithManyToOne.mustache");
            add("inputTableWithOneToMany.mustache");
            add("inputTableWithOneToOne.mustache");
            add("listTable.mustache");
            add("listTableManyToMany.mustache");
            add("listTableManyToOne.mustache");
            add("login.mustache");
            add("optionModel.mustache");
            add("optionUtil.mustache");
            add("paginate.mustache");
            add("paginateSetting.mustache");
            add("paging.mustache");
            add("pagingBean.mustache");
            add("routesTagLogin.mustache");
            add("routesTagReg.mustache");
            add("routesTagScaffold.mustache");
            add("routesTagTop.mustache");
            add("searchEngine.mustache");
            add("searchPaginate.mustache");
            add("textareaTable.mustache");
        }};
        String writePath = "kurad/templates";
        String readPath = "";
        String result;
        try {
            result = copyFileFromResource(writePath, readPath, fileList);
        } catch (IOException e) {
            result = "fail template copy";
            e.printStackTrace();
        }
        return result;
    }

    /**
     * copy file from resources dir
     * @param writePath
     * @param readPath
     * @param fileList
     * @return
     * @throws IOException
     */
    private static String copyFileFromResource(String writePath, String readPath, List<String> fileList) throws IOException {
        CrudResult result = new CrudResult();
        for(String file : fileList) {
            File writeFilePath = new File(writePath + FileUtil.DIR_SEPARATOR + file);
            File writeFileDir = new File(writeFilePath.getParent());
            if(!writeFileDir.exists()) {
                if(!writeFileDir.mkdirs()) {
                    return result.report();
                }
            }
            FileUtil.copyFileFromResource(writePath + FileUtil.DIR_SEPARATOR + file, readPath + FileUtil.DIR_SEPARATOR + file, null);
            result.add(Colors.green("[kurad : created file] ") + writePath + FileUtil.DIR_SEPARATOR + file);
        }
        return result.report();
    }

}

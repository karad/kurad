package jp.greative.kurad.app.controller.writer;

import jp.greative.util.OptionUtil;
import play.libs.F;
import java.util.Arrays;
import java.util.List;

/**
 * View Method
 */
public enum ViewMethod {
    INDEX("index") {
        @Override
        public List<String> apply(Matcher m) {
            return m.caseIsIndex();
        }
    },
    CREATE("create") {
        @Override
        public List<String> apply(Matcher m) {
            return m.caseIsCreate();
        }
    },
    UPDATE("update") {
        @Override
        public List<String> apply(Matcher m) {
            return m.caseIsUpdate();
        }
    },
    DETAIL("detail") {
        @Override
        public List<String> apply(Matcher m) {
            return m.caseIsDetail();
        }
    },
    SEARCH("search") {
        @Override
        public List<String> apply(Matcher m) {
            return m.caseIsSearch();
        }
    };

    private String name;
    private String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public interface Matcher {
        List<String> caseIsIndex();
        List<String> caseIsCreate();
        List<String> caseIsUpdate();
        List<String> caseIsDetail();
        List<String> caseIsSearch();
    }

    private ViewMethod(String s) {
        this.name = s;
    }

    public abstract List<String> apply(Matcher m);

    public static F.Option<ViewMethod> getEnum(String str) {
        List<ViewMethod> list = Arrays.asList(ViewMethod.values());
        for(ViewMethod e : list) {
            if (str.equals(e.name)){
                return OptionUtil.apply(e);
            }
        }
        return new F.None<ViewMethod>();
    }
}
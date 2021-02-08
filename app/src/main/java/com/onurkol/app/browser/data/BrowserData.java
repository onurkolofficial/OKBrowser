package com.onurkol.app.browser.data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class BrowserData {
    // OK Browser Default Settings
    // <BROWSER_LICENSE>
    public static final int BROWSER_LICENSE=0;

    // User Agent
    public static final String DESKTOP_USER_AGENT="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";

    // Settings: {1} ON | {0} OFF
    // Javascript Mode
    public static int BROWSER_JAVASCRIPT_MODE=1;
    // Geo Location
    public static int BROWSER_GEO_LOCATION=1;
    // Popups
    public static int BROWSER_POPUPS=0;
    // Local Storage
    public static int BROWSER_LOCAL_STORAGE=1;
    // Zoom
    public static int BROWSER_ZOOM=1;
    // Zoom Buttons
    public static int BROWSER_ZOOM_BUTTONS=0;
    // Cache
    public static int BROWSER_APP_CACHE=1;
    // Form Data
    public static int BROWSER_SAVE_FORMS=1;
    // Incognito Mode
    public static int BROWSER_INCOGNITO_MODE=0;
    // Desktop Mode
    public static int BROWSER_DESKTOP_MODE=0;

    // History List
    public static List<HistoryDaysData> BROWSER_HISTORY_DAYS=new ArrayList<HistoryDaysData>();
    public static List<HistoryData> BROWSER_HISTORY_SITES=new ArrayList<HistoryData>();

    // Bookmark List
    public static List<BookmarksData> BROWSER_BOOKMARKS=new ArrayList<BookmarksData>();

    // Don't edit!
    public static String BROWSER_REFRESH_MODE="";
    public static boolean BROWSER_REFRESH_STATUS=false;
    public static boolean BROWSER_START_NEW_TAB=false;

    // Search Engines
    public static int BROWSER_SEARCH_ENGINE=0; // Default Engine (Google)

    public static final List<String> SEARCH_ENGINES=new ArrayList<String>();
    public static final List<String> SEARCH_ENGINE_URLS=new ArrayList<String>();
    public static final List<String> SEARCH_ENGINE_HOME_URLS=new ArrayList<String>();

    public static void setupSearchEngines(){
        // Search Engine Names
        SEARCH_ENGINES.add(0,"Google");
        SEARCH_ENGINES.add(1,"Yandex");
        SEARCH_ENGINES.add(2,"Bing");
        SEARCH_ENGINES.add(3,"Baidu");
        SEARCH_ENGINES.add(4,"DuckDuckGo");
        SEARCH_ENGINES.add(5,"Ecosia");

        // Search Engine URLS
        SEARCH_ENGINE_URLS.add(0,"https://www.google.com/search?q=");
        SEARCH_ENGINE_URLS.add(1,"https://www.yandex.com/search/?text=");
        SEARCH_ENGINE_URLS.add(2,"http://www.bing.com/search?q=");
        SEARCH_ENGINE_URLS.add(3,"https://www.baidu.com/s?wd=");
        SEARCH_ENGINE_URLS.add(4,"https://duckduckgo.com/?q=");
        SEARCH_ENGINE_URLS.add(5,"https://www.ecosia.org/search?q=");

        // Search Engine Home URLS
        SEARCH_ENGINE_HOME_URLS.add(0,"https://www.google.com/");
        SEARCH_ENGINE_HOME_URLS.add(1,"https://www.yandex.com/");
        SEARCH_ENGINE_HOME_URLS.add(2,"http://www.bing.com/");
        SEARCH_ENGINE_HOME_URLS.add(3,"https://www.baidu.com/");
        SEARCH_ENGINE_HOME_URLS.add(4,"https://duckduckgo.com/");
        SEARCH_ENGINE_HOME_URLS.add(5,"https://www.ecosia.org/");
    }

    // Check URL Keywords
    private static final String URL_ABOUT_BLANK = "about:blank";
    private static final String URL_SCHEME_FILE = "file://";
    private static final String URL_SCHEME_MAIL_TO = "mailto:";

    public static boolean isURL(String url) {
        if (url == null) {
            return false;
        }
        url = url.toLowerCase(Locale.getDefault());
        if (url.startsWith(URL_ABOUT_BLANK)
                || url.startsWith(URL_SCHEME_MAIL_TO)
                || url.startsWith(URL_SCHEME_FILE)) {
            return true;
        }

        String regex = "^((ftp|http|https|intent)?://)"                      // support scheme
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"                            // IP形式的URL -> 199.194.52.184
                + "|"                                                        // 允许IP和DOMAIN（域名）
                + "(.)*"                                                     // 域名 -> www.
                + "([0-9a-z_!~*'()-]+\\.)*"                                  // 域名 -> www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\."                    // 二级域名
                + "[a-z]{2,6})"                                              // first level domain -> .com or .museum
                + "(:[0-9]{1,4})?"                                           // Port -> :80
                + "((/?)|"                                                   // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(url).matches();
    }

    public static String convertURL(String url) {
        String outUrl;
        if (url == null)
            return "";
        if(!url.contains("http") || !url.contains("https"))
            outUrl="https://"+url;
        else
            outUrl=url;

        return outUrl;
    }
}

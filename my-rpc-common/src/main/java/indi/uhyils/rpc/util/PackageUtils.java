package indi.uhyils.rpc.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月29日 07时14分
 */
public class PackageUtils {

    /**
     * 文件
     */
    private static final String FILE = "file";
    /**
     * jar包
     */
    private static final String JAR = "jar";

    private PackageUtils() {
    }

    /**
     * 根据包名获取class
     *
     * @param packageName         包名
     * @param excludePackage      排除的包名
     * @param recursiveSubpackage 是否递归子包
     * @return
     */
    public static Set<Class<?>> getClassByPackageName(String packageName, String[] excludePackage, Boolean recursiveSubpackage) throws IOException, ClassNotFoundException {

        if (excludePackage == null) {
            excludePackage = new String[0];
        }
        List<String> excludePackagePaths = Arrays.stream(excludePackage).map(t -> t.replace(".", "/")).collect(Collectors.toList());

        Set<Class<?>> fileNames = new HashSet<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        doDealWithScanPackage(recursiveSubpackage, excludePackagePaths, fileNames, loader, packageName);
        return fileNames;
    }

    /**
     * 根据包名获取class
     *
     * @param packageName         包名
     * @param excludePackage      排除的包名
     * @param recursiveSubpackage 是否递归子包
     * @return
     */
    public static Set<Class<?>> getClassByPackageName(String[] packageName, String[] excludePackage, Boolean recursiveSubpackage) throws IOException, ClassNotFoundException {

        // 去重(覆盖的包)
        packageName = duplicateRemoval(packageName);
        excludePackage = duplicateRemoval(excludePackage);

        List<String> excludePackagePaths = Arrays.stream(excludePackage).map(t -> t.replace(".", "/")).collect(Collectors.toList());

        Set<Class<?>> fileNames = new HashSet<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (String packageNameItem : packageName) {
            doDealWithScanPackage(recursiveSubpackage, excludePackagePaths, fileNames, loader, packageNameItem);
        }

        return fileNames;
    }

    /**
     * 根据包名获取class
     *
     * @param packageName         包名
     * @param excludePackage      排除的包名
     * @param recursiveSubpackage 是否递归子包
     * @return
     */
    public static Set<Class<?>> getClassByPackageName(Collection<String> packageName, Collection<String> excludePackage, Boolean recursiveSubpackage) {

        // 去重(覆盖的包)
        packageName = duplicateRemoval(packageName);
        excludePackage = duplicateRemoval(excludePackage);

        List<String> excludePackagePaths = excludePackage.stream().map(t -> t.replace(".", "/")).collect(Collectors.toList());

        Set<Class<?>> fileNames = new HashSet<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            for (String packageNameItem : packageName) {
                doDealWithScanPackage(recursiveSubpackage, excludePackagePaths, fileNames, loader, packageNameItem);
            }
        } catch (IOException | ClassNotFoundException e) {
            LogUtil.error(PackageUtils.class, e);
        }

        return fileNames;
    }

    /**
     * 扫描
     *
     * @param recursiveSubpackage 是否递归子包
     * @param excludePackagePaths 排除的包
     * @param result              结果
     * @param loader              类加载器
     * @param packageNameItem     要扫描的包名
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void doDealWithScanPackage(Boolean recursiveSubpackage, List<String> excludePackagePaths, Set<Class<?>> result, ClassLoader loader, String packageNameItem) throws IOException, ClassNotFoundException {
        String packagePath = packageNameItem.replace(".", "/");
        Enumeration<URL> urls = loader.getResources(packagePath);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if (url == null || !checkExclude(excludePackagePaths, url)) {
                continue;
            }

            String type = url.getProtocol();
            if (FILE.equals(type)) {
                result.addAll(getClassNameByFile(url.getPath(), recursiveSubpackage));
            } else if (JAR.equals(type)) {
                result.addAll(getClassNameByJar(url.getPath(), recursiveSubpackage));
            }
        }
        result.addAll(getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, recursiveSubpackage));
    }

    /**
     * 去重包(包含覆盖)
     *
     * @param packageName 包名
     * @return 去重后的数组
     */
    private static String[] duplicateRemoval(String[] packageName) {
        List<String> result = duplicateRemoval(Arrays.asList(packageName));
        return result.toArray(new String[0]);
    }

    /**
     * 去重包(包含覆盖)
     *
     * @param packageName 包名
     * @return 去重后的数组
     */
    private static List<String> duplicateRemoval(Collection<String> packageName) {
        // 根据 . 的数量排序
        List<String> collects = packageName.stream().sorted(Comparator.comparingInt(t -> StringUtils.countMatches(t, "."))).collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for (String collect : collects) {
            boolean cover = Boolean.FALSE;
            for (String resultItem : result) {
                if (resultItem.startsWith(collect)) {
                    cover = Boolean.TRUE;
                    break;
                }
            }
            // 如果没有cover
            if (!cover) {
                result.add(collect);
            }
        }
        return result;
    }

    private static Boolean checkExclude(List<String> excludePackagePaths, URL url) {
        for (String excludePackagePath : excludePackagePaths) {
            if (url.getPath().contains(excludePackagePath)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath     文件路径
     *                     类名集合
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     * @throws UnsupportedEncodingException
     */
    private static List<Class<?>> getClassNameByFile(String filePath, boolean childPackage) throws UnsupportedEncodingException, ClassNotFoundException {
        // 获取path下的所有文件或文件夹
        List<Class<?>> myClassName = new ArrayList<>();
        filePath = URLDecoder.decode(filePath, "UTF-8");
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null) {
            return myClassName;
        }

        // 遍历所有获取的文件,获取所有类
        for (File childFile : childFiles) {
            if (childFile.isDirectory() && childPackage) {
                myClassName.addAll(getClassNameByFile(childFile.getPath(), Boolean.TRUE));
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath = childFilePath.replace("\\", "/");
                    int packageStartIndex = 0;
                    int classIndex = childFilePath.lastIndexOf("/classes/");
                    int testClassIndex = childFilePath.lastIndexOf("/test-classes/");
                    if (classIndex > testClassIndex) {
                        packageStartIndex = classIndex + 9;
                    } else {
                        packageStartIndex = testClassIndex + 14;
                    }
                    childFilePath = childFilePath.substring(packageStartIndex, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("/", ".");
                    myClassName.add(Class.forName(childFilePath));
                }
            }
        }
        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath      jar文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     * @throws UnsupportedEncodingException
     */
    private static List<Class<?>> getClassNameByJar(String jarPath, boolean childPackage) throws UnsupportedEncodingException {
        List<Class<?>> myClassName = new ArrayList<>();
        String[] jarInfo = jarPath.split("!");
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
        jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
        String packagePath = jarInfo[1].substring(1);
        try (JarFile jarFile = new JarFile(jarFilePath)) {

            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    if (childPackage) {
                        if (entryName.startsWith(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(Class.forName(entryName));
                        }
                    } else {
                        int index = entryName.lastIndexOf("/");
                        String myPackagePath;
                        if (index != -1) {
                            myPackagePath = entryName.substring(0, index);
                        } else {
                            myPackagePath = entryName;
                        }
                        if (myPackagePath.equals(packagePath)) {
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                            myClassName.add(Class.forName(entryName));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myClassName;
    }

    /**
     * 从所有jar中搜索该包，并获取该包下所有类
     *
     * @param urls         URL集合
     * @param packagePath  包路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     * @throws UnsupportedEncodingException
     */
    private static List<Class<?>> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) throws UnsupportedEncodingException {
        List<Class<?>> myClassName = new ArrayList<>();
        if (urls != null) {
            for (int i = 0; i < urls.length; i++) {
                URL url = urls[i];
                String urlPath = url.getPath();
                // 不必搜索classes文件夹
                if (urlPath.endsWith("classes/")) {
                    continue;
                }
                String jarPath = urlPath + "!/" + packagePath;
                myClassName.addAll(getClassNameByJar(jarPath, childPackage));
            }
        }
        return myClassName;
    }
}

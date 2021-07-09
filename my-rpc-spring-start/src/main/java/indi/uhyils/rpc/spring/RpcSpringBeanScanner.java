package indi.uhyils.rpc.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.annotation.Annotation;

/**
 * 指定注解的扫描器,借鉴mybatis-spring-start,有filter,可以手动选中哪一种需要扫描,哪一种不需要扫描
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月15日 14时53分
 */
public class RpcSpringBeanScanner extends ClassPathBeanDefinitionScanner {

    /**
     * 要被扫描的注解
     */
    private Class<? extends Annotation> annotationClass;

    /**
     * 父类是这个接口的类
     */
    private Class<?> superInterface;

    public RpcSpringBeanScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {
        boolean acceptAllInterfaces = Boolean.TRUE;

        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = Boolean.FALSE;
        }
        if (this.superInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.superInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return Boolean.FALSE;
                }
            });
            acceptAllInterfaces = Boolean.FALSE;
        }
        if (acceptAllInterfaces) {
            addIncludeFilter((metadataReader, metadataReaderFactory) -> Boolean.TRUE);
        }

    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public Class<?> getSuperInterface() {
        return superInterface;
    }

    public void setSuperInterface(Class<?> superInterface) {
        this.superInterface = superInterface;
    }
}

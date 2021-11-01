package cn.bugstack.springframework.beans.service.support;

import cn.bugstack.springframework.beans.factory.config.BeanReference;
import cn.bugstack.springframework.beans.factory.config.BeanDefinition;
import cn.bugstack.springframework.beans.resolver.config.Resource;
import cn.bugstack.springframework.beans.resolver.service.ResourceLoader;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//book代码可以解决先注册后由第一次调用get时候生成单例对象
//可以乱序注册bean

import static java.rmi.registry.LocateRegistry.getRegistry;

public class XmlBeanDefinitionReader implements BeanDefinitionReader {
    private ResourceLoader resourceLoader;

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Map<String, BeanDefinition> resource2BeanDefinitions(String location) throws ClassNotFoundException {
        Resource resource = resourceLoader.getResource(location);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        Map<String, BeanDefinition> producedBeanDefinition = new ConcurrentHashMap<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            // 判断元素
            if (!(childNodes.item(i) instanceof Element)) continue;
            // 判断对象
            if (!"bean".equals(childNodes.item(i).getNodeName())) continue;

            // 解析标签
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");
            String beanScope = bean.getAttribute("scope");

            // 获取 Class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级 id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }
            // 读取属性并填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) continue;
                // 解析标签：property
                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
//                Property propertyValue = new Property(attrName, value);
                beanDefinition.getBeanDefinitionProperties().addPropertyValue(attrName, value);
            }

            producedBeanDefinition.put(beanName, beanDefinition);
//            if (getRegistry().containsBeanDefinition(beanName)) {
//                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
//            }
//             //注册 BeanDefinition
//            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
        return producedBeanDefinition;
    }
}

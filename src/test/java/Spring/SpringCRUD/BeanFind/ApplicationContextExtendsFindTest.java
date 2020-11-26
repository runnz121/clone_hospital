package Spring.SpringCRUD.BeanFind;

import Spring.SpringCRUD.discount.DiscountPolicy;
import Spring.SpringCRUD.discount.FixDiscountPolicy;
import Spring.SpringCRUD.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.spliterator;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Configuration
    static class TestConfig{
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }

 /**   @Test
    @DisplayName("자식이 둘 이상인 부모 타입 조회시")
    void findBeanByParentTypeDuplicate (){
        DiscountPolicy bean = (DiscountPolicy) ac.getBeansOfType(DiscountPolicy.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(NoUniqueBeanDefinitionException.class) );

    }*/

    @Test
    @DisplayName("자식이 둘 이상이면 빈 이름을 지정하면 된다")
    void findBeanByParentTypeBeanName (){
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);

    }

    @Test
    @DisplayName("특정하위타입 조회")
    void findBeanBySubType (){
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모타입으로 모두 조회")
    void findAllBeanByParentType (){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key  = " + key + "value ="+ beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모타입으로 모두 조회 - object")
    void findAllBeanByObjectType (){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key+ "value"+beansOfType.get(key));
        }

    }

    }


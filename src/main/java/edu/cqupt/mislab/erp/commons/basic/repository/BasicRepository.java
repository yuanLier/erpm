package edu.cqupt.mislab.erp.commons.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * @Author: chuyunfei
 * @Date: 2019/3/7 19:22
 * @Description: 基本的Repository接口，项目的所有Repository需要继承这个Repository以实现数据的验证
 **/

@NoRepositoryBean
public interface  BasicRepository<V,K extends Serializable> extends JpaRepository<V,K> {

    /**
    1、重写save方法以保证所有实体必须通过参数校验才能被持久化
    2、另外两个个方法save(Iterable<S> entities)、saveAndFlush(S entity)也是在底层调用这个方法
     */
    @Override
    <S extends V> S save(@Validated S entity);
}

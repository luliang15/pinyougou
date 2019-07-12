package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * @program:PinYouGou01
 * @description:实现思路：我们将规格和规格选项数据合并成一个对象来传递，这时我们需要用一个对象将这两个对象组合起来。在业务逻辑中，得到组合对象中的规格和规格选项列表，插入规格返回规格ID，然后循环插入规格选项。
 * @author:Mr.lu
 * @create:2019-07-12 17:52
 **/
    public class Specification implements Serializable {
    private TbSpecification specification;
    private List<TbSpecificationOption> specificationOptionList;

    public TbSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }

    public List<TbSpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }

}

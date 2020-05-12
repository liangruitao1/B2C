package com.turing.b2c.model.pojo.union;

import com.turing.b2c.model.pojo.Specification;
import com.turing.b2c.model.pojo.SpecificationOption;

import java.io.Serializable;
import java.util.List;

public class SpecificationUnion implements Serializable {
    private static final  long serialVersionUID = 1L;
    private Specification spec;
    private List<SpecificationOption> specOptionList;

    public List<SpecificationOption> getSpecOptionList() {
        return specOptionList;
    }

    public void setSpecOptionList(List<SpecificationOption> specOptionList) {
        this.specOptionList = specOptionList;
    }

    public SpecificationUnion(){

    }

    public SpecificationUnion(Specification spec,List<SpecificationOption> specList){
        this.spec=spec;
        this.specOptionList=specList;
    }

    public Specification getSpec() {
        return spec;
    }

    public void setSpec(Specification spec) {
        this.spec = spec;
    }





}

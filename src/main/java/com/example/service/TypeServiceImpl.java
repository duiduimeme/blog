package com.example.service;

import com.example.NotFoundException;
import com.example.dao.TypeRepository;
import com.example.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typerepository;


    @Transactional
    @Override
    public Type saveType(Type type) {

        return typerepository.save(type);
    }


    @Transactional
    @Override
    public Type getType(Long id) {
        return typerepository.findOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typerepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typerepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typerepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return typerepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typerepository.findOne(id);
        if(t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);

        return typerepository.save(t);
    }


    @Transactional
    @Override
    public void deleteType(Long id) {
        typerepository.delete(id);
    }
}

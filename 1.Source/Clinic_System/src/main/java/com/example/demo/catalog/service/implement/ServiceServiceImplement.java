package com.example.demo.catalog.service.implement;

import com.example.demo.app.entity.GroupMaster;
import com.example.demo.app.entity.ServiceMaster;
import com.example.demo.app.repository.GroupMasterRepository;
import com.example.demo.catalog.dto.ServiceDto;
import com.example.demo.catalog.repository.ServiceMasterRepository;
import com.example.demo.catalog.request.ServiceRes;
import com.example.demo.catalog.response.PageResponse;
import com.example.demo.catalog.service.ServiceService;
import com.example.demo.catalog.vo.ServiceSearchReq;
import com.example.demo.common.Constants;
import org.apache.commons.collections.ArrayStack;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImplement extends AbstractService implements ServiceService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ServiceMasterRepository serviceMasterRepository;

    @Autowired
    private GroupMasterRepository groupMasterRepository;

    @SuppressWarnings("unchecked")
    @Override
    public List<ServiceDto> getAll() {
        //Pageable pageable = PageRequest.of()
        try {
            StringBuilder sqlGetAll = new StringBuilder("select new com.example.demo.catalog.dto.ServiceDto(s, g) from ServiceMaster s " +
                    "inner join GroupMaster g on s.groupId = g.id");
            Query query = entityManager.createQuery(sqlGetAll.toString(), ServiceDto.class);
            List<ServiceDto> result = query.getResultList();
            if(result != null)
               return  result;
        } catch (Exception e){
            logger.error("get all with error : {} | {}", e.getMessage(), e.getMessage().toString());
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageResponse getData(ServiceSearchReq serviceSearchReq) {
        try {
            PageResponse response = new PageResponse();
            Pageable paging = PageRequest.of(serviceSearchReq.getPage() - 1, Constants.PAGE_SIZE);
            StringBuilder sqlQ = new StringBuilder(
                    "select new com.example.demo.catalog.dto.ServiceDto(s, g) from ServiceMaster s " +
                    "inner join GroupMaster g on s.groupId = g.id ");
            StringBuilder sqlC = new StringBuilder(
                    "select count(s.id)  from ServiceMaster s " +
                            "inner join GroupMaster g on s.groupId = g.id ");
            if(StringUtils.isNotEmpty(serviceSearchReq.getKey())){
                sqlQ.append("where (s.serviceName like :serviceName ");
                sqlQ.append("or g.groupName like :groupName) ");
                sqlC.append("where (g.groupName like :groupName ");
                sqlC.append("or s.serviceName like :serviceName) ");
            }
            Query query = entityManager.createQuery(sqlQ.toString(), ServiceDto.class);
            query.setFirstResult(Constants.PAGE_SIZE * (serviceSearchReq.getPage() - 1));
            query.setMaxResults(Constants.PAGE_SIZE);
            Query queryC = entityManager.createQuery(sqlC.toString());
            if(StringUtils.isNotEmpty(serviceSearchReq.getKey())){
                query.setParameter("serviceName", "%" + serviceSearchReq.getKey() + "%");
                query.setParameter("groupName", "%" + serviceSearchReq.getKey() + "%");
                queryC.setParameter("serviceName", "%" + serviceSearchReq.getKey() + "%");
                queryC.setParameter("groupName", "%" + serviceSearchReq.getKey() + "%");
            }
            List<ServiceDto> result = query.getResultList();
            long total = (long) queryC.getSingleResult();
            if(result != null){
                Page<ServiceDto> page = new PageImpl<>(result,paging,total);
                response.setItems(result);
                response.setTotalItems((int)total);
                response.setCurrentPage(serviceSearchReq.getPage());
                response.setTotalPages(page.getTotalPages());
                return response;
            }
        }catch (Exception e){
            logger.error("get all with paging with error : {} | {}", e.getMessage(), e.getMessage().toString());
        }
        return null;
    }

    @Override
    public PageResponse getDataByPage(ServiceSearchReq serviceSearchReq) {
        PageResponse pageResponse = new PageResponse();
        int page = 1;
        if(serviceSearchReq.getPage() != null){
            page = serviceSearchReq.getPage();
        }
        int offset = page - 1;
        Pageable pageable = PageRequest.of(offset, Constants.PAGE_SIZE);
        String key = "";
        if(StringUtils.isNotEmpty(serviceSearchReq.getKey())){
            key = serviceSearchReq.getKey();
        }
        Page<ServiceMaster> services = serviceMasterRepository.getDataByName(key, pageable);
        List<ServiceRes> serviceRes = new ArrayStack();
        for (ServiceMaster service:services){
            ServiceRes res = new ServiceRes();
            res.setId(service.getId());
            res.setName(service.getServiceName());
            res.setSpecializedId(service.getGroupId());
            res.setCode(service.getCode());
            Optional<GroupMaster> groupMaster = groupMasterRepository.findById(service.getGroupId());
            if(groupMaster.isPresent()){
                res.setSpecialized(groupMaster.get().getGroupName());
            }
            serviceRes.add(res);
        }
        pageResponse.setTotalPages(services.getTotalPages());
        pageResponse.setItems(serviceRes);
        pageResponse.setCurrentPage(page);
        return pageResponse;
    }

    @Override
    public boolean deleteServiceById(long id) {
        try {
            if(id > 0) {
                Optional<ServiceMaster> serviceMasterOptional = serviceMasterRepository.findById(id);
                if(serviceMasterOptional.isPresent()){
                    serviceMasterRepository.deleteById(id);
                    return true;
                }
            }
        }catch (Exception e){
            logger.error("deleteServiceById error : {} | {}", e.getMessage(), e.getMessage().toString());
        }
        return false;
    }

    @Override
    public void addEditService(ServiceRes serviceRes) {
        ServiceMaster service = null;
        if(serviceRes.getId() != null){
            Optional<ServiceMaster> service1 = serviceMasterRepository.findById(serviceRes.getId());
            if(service1.isPresent()){
                service = service1.get();
            }
        }else{
            service = new ServiceMaster();
        }
        service.setServiceName(serviceRes.getName());
        service.setGroupId(serviceRes.getSpecializedId());
        service.setCode(serviceRes.getCode());
        serviceMasterRepository.save(service);
    }
}

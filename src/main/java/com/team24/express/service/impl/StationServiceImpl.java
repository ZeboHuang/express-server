package com.team24.express.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.team24.express.entity.Delivery;
import com.team24.express.entity.Station;
import com.team24.express.entity.StationDelivery;
import com.team24.express.entity.StationEmployee;
import com.team24.express.mapper.StationMapper;
import com.team24.express.service.StaitonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StationServiceImpl implements StaitonService {
    // jjw code

    @Autowired
    StationMapper stationMapper;

    @Override
    public List<Delivery> selectPackages(Delivery order) {
        return stationMapper.selectPackagesByConditions(order);
    }

    @Override
    public void packageInRep(StationDelivery stationDelivery) {
        stationDelivery.setCreateTime(LocalDateTime.now());
        stationDelivery.setUpdateTime(LocalDateTime.now());
        stationDelivery.setStatus(StationDelivery.STATUS_UNCLAIMED);
        stationMapper.addNewPackage(stationDelivery);
    }

    @Override
    public void packageOutRep(StationDelivery stationDelivery) {
        stationDelivery.setUpdateTime(LocalDateTime.now());
        stationMapper.updatePackageCondition(stationDelivery);
    }

    @Override
    public void addNewCourier(StationEmployee e) {
        stationMapper.addNewCourier(e);
    }

    @Override
    public List<StationEmployee> searchCourier(Integer status, Long id) {
        return stationMapper.selectCourierByCondition(status, id);
    }

    @Override
    public void deleteCourier(Long id) {
        stationMapper.deleteEmployeeById(id);
    }

    // zgd code

    @Override
    public Station selectByStationname(String username) {
        QueryWrapper<Station> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        return stationMapper.selectOne(wrapper);
    }

    @Override
    public List<Station> getStationList() {
        QueryWrapper<Station> wrapper = new QueryWrapper<>();
        return stationMapper.selectList(wrapper);
    }

    @Override
    public List<Station> queryByAddress(String address) {
        String[] parts = address.split("/");
        if (parts.length > 2) {
            String province = parts[0];
            String city = parts[1];
            String country = parts[2];
            String street = "";
            if (parts.length > 3) {
                street = parts[3];
            }
            QueryWrapper<Station> wrapper = new QueryWrapper<>();
            wrapper.eq("province", province);
            wrapper.eq("city", city);
            wrapper.eq("country", country);
            wrapper.like("street", street);
            return stationMapper.selectList(wrapper);
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return stationMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteBatchIds(List<Long> ids) {
        return stationMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public int insert(Station station) {
        return stationMapper.insert(station);
    }

    @Override
    public int update(Station station) {
        return stationMapper.updateById(station);
    }

    @Override
    public List<Station> queryList() {
        return stationMapper.selectAllList();
    }

}

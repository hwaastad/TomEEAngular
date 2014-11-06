/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.javaeeangular.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.waastad.javaeeangular.model.ChartData;
import org.waastad.javaeeangular.model.Customer;

/**
 *
 * @author Helge Waastad <helge.waastad@waastad.org>
 */
@Singleton
@Slf4j
public class ApplicationBean {

    private List<Customer> customerList;
    private List<ChartData> chartData;
    private List<ChartData> pieChartData;
    private static final Random random = new Random(1000);

    public List<ChartData> getPieChartData() {
        return pieChartData;
    }

    @PostConstruct
    public void init() {
        log.info("Intitializing customer list,preloading a couple");
        Customer c1 = new Customer(1, "K1");
        Customer c2 = new Customer(2, "K2");
        Customer c3 = new Customer(3, "K3");
        Customer c4 = new Customer(4, "K4");
        customerList = new ArrayList<>();
        customerList.addAll(Arrays.asList(c1, c2, c3, c4));

        ChartData d1 = new ChartData("1959", "-0.307");
        ChartData d2 = new ChartData("1965", "-0.107");
        ChartData d3 = new ChartData("1970", "2.307");
        ChartData d4 = new ChartData("1975", "4.307");
        chartData = new ArrayList<>();
        chartData.addAll(Arrays.asList(d1, d2, d3, d4));

        ChartData d5 = new ChartData("1959", "17");
        ChartData d6 = new ChartData("1965", "20");
        ChartData d7 = new ChartData("1970", "2");
        ChartData d8 = new ChartData("1975", "25");
        pieChartData = new ArrayList<>();
        pieChartData.addAll(Arrays.asList(d5, d6, d7, d8));
    }

    public List<ChartData> getChartData() {
        return chartData;
    }

    public List<Customer> getCustomers() {
        log.info("Getting customers");
        return customerList;
    }

    public Customer getCustomer(Integer id) {
        return findCustomer(customerList, id);
    }

    public Customer modifyCustomer(Integer customerId, Customer customer) {
        Customer findCustomer = findCustomer(customerList, customerId);
        if (findCustomer == null) {
            log.error("Customer {} not found", customerId);
            return null;
        } else {
            findCustomer.setName(customer.getName());
            return findCustomer;
        }
    }

    public void addToList(Customer customer) {
        customer.setId(random.nextInt());
        log.info("Adding customer: {}", customer);
        customerList.add(customer);
    }

    public void removeFromList(Integer customerId) {
        log.info("Removing customerId: {}", customerId);
        Customer found = findCustomer(customerList, customerId);
        if (found == null) {
            log.info("Customer not found");
        } else {
            customerList.remove(found);
        }
    }

    private Customer findCustomer(List<Customer> list, final Integer id) {
        return CollectionUtils.find(list, new Predicate<Customer>() {

            @Override
            public boolean evaluate(Customer t) {
                return t.getId().equals(id);
            }
        });
    }
}

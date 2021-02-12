package com.codmind.api_order.services;

import com.codmind.api_order.entity.Order;
import com.codmind.api_order.entity.OrderLine;
import com.codmind.api_order.entity.Product;
import com.codmind.api_order.entity.User;
import com.codmind.api_order.exceptions.DataServiceException;
import com.codmind.api_order.exceptions.GeneralServiceException;
import com.codmind.api_order.exceptions.ValidateServiceException;
import com.codmind.api_order.repository.OrderLineRepository;
import com.codmind.api_order.repository.OrderRepository;
import com.codmind.api_order.repository.ProductRepository;
import com.codmind.api_order.security.UserPrincipal;
import com.codmind.api_order.validator.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Order> findAll(Pageable page){
        try{
            List<Order> order = orderRepository.findAll(page).toList();
            return order;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public Order findById(Long id){
        try{
            log.debug("find order by id:{}", id);
            Order order = orderRepository.findById(id).orElseThrow(() -> new DataServiceException("The Order does no exist"));
            return order;
        }catch (ValidateServiceException | DataServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw  new GeneralServiceException(e.getMessage(), e);
        }
    }
    @Transactional
    public Order createOrUpdate(Order order){
        try{
            OrderValidator.createOrUpdate(order);

            /*UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/
            User user = UserPrincipal.getAuthUser();

            double total = 0;
            for(OrderLine line : order.getLines()) {
                Product product = productRepository.findById(line.getProduct().getId())
                        .orElseThrow(() -> new DataServiceException("No existe el producto " + line.getProduct().getId()));
                line.setPrice(product.getPrice());
                line.setTotal(product.getPrice() * line.getQuantity());
                total += line.getTotal();
            }

            order.setTotal(total);
            order.getLines().forEach(line -> line.setOrder(order));
            if (order.getId() == null){
                order.setUser(user);
                order.setRegDate(LocalDateTime.now());
                Order newOrder = orderRepository.save(order);
                return newOrder;
            }
            Order savedOrder = orderRepository.findById(order.getId())
                    .orElseThrow(() -> new DataServiceException("The product does not exits"));
            order.setRegDate(LocalDateTime.now());

            List<OrderLine> deleteOrderLines = savedOrder.getLines();
            //eliminara la que nos coincidan gracias al metodo equals de la entidad
            deleteOrderLines.removeAll(order.getLines());
            orderLineRepository.deleteAll(deleteOrderLines);
            return orderRepository.save(order);
        }catch (ValidateServiceException | DataServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw  new GeneralServiceException(e.getMessage(), e);
        }
    }
    @Transactional
    public void delete(Long id){
        try{
            Order order = orderRepository.findById(id).orElseThrow(() -> new DataServiceException("The Order does no exist"));
            orderRepository.delete(order);
        }catch (ValidateServiceException | DataServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}

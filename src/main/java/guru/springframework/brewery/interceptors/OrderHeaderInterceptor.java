package guru.springframework.brewery.interceptors;


import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.OrderStatusEnum;
import guru.springframework.brewery.events.BeerOrderStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Catch order updates
 */
@Slf4j
@Component
public class OrderHeaderInterceptor implements Interceptor {

    private final ApplicationEventPublisher publisher;

    public OrderHeaderInterceptor(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) throws CallbackException {
                
                if (entity instanceof BeerOrder){
                    for(Object curObj : currentState){
                        if(curObj instanceof OrderStatusEnum){
                            for (Object prevObj : previousState){
                                if (prevObj instanceof OrderStatusEnum) {
                                    OrderStatusEnum curStatus = (OrderStatusEnum) curObj;
                                    OrderStatusEnum prevStatus = (OrderStatusEnum) prevObj;
                                    
                                    if(curStatus != prevStatus){
                                        log.debug("Order status change detected");
                                        
                                        publisher.publishEvent(new BeerOrderStatusChangeEvent((BeerOrder) entity, prevStatus));
                                    }
                                }
                            }
                        }
                    }
                }
                
                return Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
            }
}

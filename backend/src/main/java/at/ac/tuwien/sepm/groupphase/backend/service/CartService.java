package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.Cart;
import at.ac.tuwien.sepm.groupphase.backend.exception.NotFoundException;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

public interface CartService {


    Cart createCart(Cart cart);

    Cart addItemToCart(Cart cart);

    Cart findCartBySessionId(UUID sessionId);


    //Cart updateCart(Cart c);

    /**
     * Find a single cart entry by sessionId.
     *
     * @param sessionId the sessionId of the session
     * @return true if sessionId is already in the database
     * @throws NotFoundException when no cart with the sessionId is found
     */
    boolean sessionIdExists(UUID sessionId);


    //@Scheduled(cron = "* */1 * * * *")
    //Long deleteCartAfterDuration();

}

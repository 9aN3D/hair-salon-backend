package pl.edu.wit.hairsalon.sharedKernel.document;

import com.querydsl.core.annotations.QueryEmbeddable;
import pl.edu.wit.hairsalon.sharedKernel.dto.CurrencyDto;

import java.math.BigDecimal;

@QueryEmbeddable
public record EmbeddableMoney(BigDecimal amount, CurrencyDto currency) {

}

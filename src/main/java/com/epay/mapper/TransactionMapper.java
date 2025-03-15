package com.epay.mapper;

import com.epay.dto.TransactionDTO;
import com.epay.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    // Map Transaction to TransactionDTO
    TransactionDTO toDTO(Transaction transaction);

    // Map TransactionDTO to Transaction
    Transaction toEntity(TransactionDTO transactionDTO);
}

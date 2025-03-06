package com.rm.leaseinsight.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class Mapper {
	private static final ModelMapper mapper;

	static {
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public Mapper() {
	}

	public static <D, T> D modelMapper(final T entity, Class<D> outClass) {
		return mapper.map(entity, outClass);
	}

	public static <D, T> List<D> modelMapperList(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream().map(entity -> modelMapper(entity, outCLass)).collect(Collectors.toList());
	}
}

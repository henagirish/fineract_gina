/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.organisation.office.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.InvalidJsonException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Deserializer of JSON for office API.
 */
@Component
public final class OfficeCommandFromApiJsonDeserializer {

	/**
	 * The parameters supported for this command.
	 */
	private final Set<String> supportedParameters = new HashSet<>(
			Arrays.asList("name", "parentId", "openingDate", "externalId", "locale", "dateFormat", "cin", "companyName",
					"companyStatus", "roc", "funds", "incorporatedDate", "registrationAddress", "registrationNumber"));

	private final FromJsonHelper fromApiJsonHelper;

	@Autowired
	public OfficeCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper) {
		this.fromApiJsonHelper = fromApiJsonHelper;
	}

	public void validateForCreate(final String json) {
		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);

		final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
				.resource("office");

		final JsonElement element = this.fromApiJsonHelper.parse(json);

		final String name = this.fromApiJsonHelper.extractStringNamed("name", element);
		baseDataValidator.reset().parameter("name").value(name).notBlank().notExceedingLengthOf(100);

		final LocalDate openingDate = this.fromApiJsonHelper.extractLocalDateNamed("openingDate", element);
		baseDataValidator.reset().parameter("openingDate").value(openingDate).notNull();

		if (this.fromApiJsonHelper.parameterExists("externalId", element)) {
			final String externalId = this.fromApiJsonHelper.extractStringNamed("externalId", element);
			baseDataValidator.reset().parameter("externalId").value(externalId).notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists("parentId", element)) {
			final Long parentId = this.fromApiJsonHelper.extractLongNamed("parentId", element);
			baseDataValidator.reset().parameter("parentId").value(parentId).notNull().integerGreaterThanZero();
		}

		/*
		 * Modification for new fields Aiswarya
		 * 
		 */
		final String cin = this.fromApiJsonHelper.extractStringNamed("cin", element);
		baseDataValidator.reset().parameter("cin").value(name).notBlank().notExceedingLengthOf(100);
		
		final String roc = this.fromApiJsonHelper.extractStringNamed("roc", element);
		baseDataValidator.reset().parameter("roc").value(roc).notBlank().notExceedingLengthOf(100);

		final LocalDate incorporatedDate = this.fromApiJsonHelper.extractLocalDateNamed("incorporatedDate", element);
		baseDataValidator.reset().parameter("incorporatedDate").value(openingDate).notNull();

		final String companyName = this.fromApiJsonHelper.extractStringNamed("companyName", element);
		baseDataValidator.reset().parameter("companyName").value(name).notBlank().notExceedingLengthOf(100);

		final String companyStatus = this.fromApiJsonHelper.extractStringNamed("companyStatus", element);
		baseDataValidator.reset().parameter("companyStatus").value(name).notBlank().notExceedingLengthOf(100);

		final String registrationAddress = this.fromApiJsonHelper.extractStringNamed("registrationAddress", element);
		baseDataValidator.reset().parameter("registrationAddress").value(name).notBlank().notExceedingLengthOf(100);

		final Long funds = this.fromApiJsonHelper.extractLongNamed("funds", element);
		baseDataValidator.reset().parameter("funds").value(funds).notNull().integerGreaterThanZero();

		final Long registrationNumber = this.fromApiJsonHelper.extractLongNamed("registrationNumber", element);
		baseDataValidator.reset().parameter("registrationNumber").value(funds).notNull().integerGreaterThanZero();

		/*
		 * Modification for new fields
		 * 
		 */
		
		throwExceptionIfValidationWarningsExist(dataValidationErrors);
	}

	private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
		if (!dataValidationErrors.isEmpty()) {
			throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
					"Validation errors exist.", dataValidationErrors);
		}
	}

	public void validateForUpdate(final String json) {
		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		this.fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, this.supportedParameters);

		final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors)
				.resource("office");

		final JsonElement element = this.fromApiJsonHelper.parse(json);

		if (this.fromApiJsonHelper.parameterExists("name", element)) {
			final String name = this.fromApiJsonHelper.extractStringNamed("name", element);
			baseDataValidator.reset().parameter("name").value(name).notBlank().notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists("openingDate", element)) {
			final LocalDate openingDate = this.fromApiJsonHelper.extractLocalDateNamed("openingDate", element);
			baseDataValidator.reset().parameter("openingDate").value(openingDate).notNull();
		}

		if (this.fromApiJsonHelper.parameterExists("externalId", element)) {
			final String externalId = this.fromApiJsonHelper.extractStringNamed("externalId", element);
			baseDataValidator.reset().parameter("externalId").value(externalId).notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists("parentId", element)) {
			final Long parentId = this.fromApiJsonHelper.extractLongNamed("parentId", element);
			baseDataValidator.reset().parameter("parentId").value(parentId).notNull().integerGreaterThanZero();
		}
		
		/*
		 * MOdification for Office Aiswarya
		 * 
		 * */

		if (this.fromApiJsonHelper.parameterExists("roc", element)) {
			final String roc = this.fromApiJsonHelper.extractStringNamed("roc", element);
			baseDataValidator.reset().parameter("roc").value(roc).notBlank().notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists("incorporatedDate", element)) {
			final LocalDate incorporatedDate = this.fromApiJsonHelper.extractLocalDateNamed("incorporatedDate", element);
			baseDataValidator.reset().parameter("incorporatedDate").value(incorporatedDate).notNull();
		}

		if (this.fromApiJsonHelper.parameterExists("registrationNumber", element)) {
			final Long registrationNumber = this.fromApiJsonHelper.extractLongNamed("registrationNumber", element);
			baseDataValidator.reset().parameter("registrationNumber").value(registrationNumber).notNull().integerGreaterThanZero();
		}

		if (this.fromApiJsonHelper.parameterExists("funds", element)) {
			final Long funds = this.fromApiJsonHelper.extractLongNamed("funds", element);
			baseDataValidator.reset().parameter("funds").value(funds).notNull().integerGreaterThanZero();
		}
		
		
		if (this.fromApiJsonHelper.parameterExists("companyName", element)) {
			final String roc = this.fromApiJsonHelper.extractStringNamed("roc", element);
			baseDataValidator.reset().parameter("roc").value(roc).notBlank().notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists("companyStatus", element)) {
			final LocalDate companyStatus = this.fromApiJsonHelper.extractLocalDateNamed("companyStatus", element);
			baseDataValidator.reset().parameter("companyStatus").value(companyStatus).notNull();
		}
		if (this.fromApiJsonHelper.parameterExists("registrationAddress", element)) {
			final String registrationAddress = this.fromApiJsonHelper.extractStringNamed("registrationAddress", element);
			baseDataValidator.reset().parameter("registrationAddress").value(registrationAddress).notBlank().notExceedingLengthOf(100);
		}

		if (this.fromApiJsonHelper.parameterExists("cin", element)) {
			final String registrationAddress = this.fromApiJsonHelper.extractStringNamed("cin", element);
			baseDataValidator.reset().parameter("cin").value(registrationAddress).notBlank().notExceedingLengthOf(100);
		}

		
		/**/
		

		throwExceptionIfValidationWarningsExist(dataValidationErrors);
	}
}
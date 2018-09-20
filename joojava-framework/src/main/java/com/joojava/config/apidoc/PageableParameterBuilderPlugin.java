package com.joojava.config.apidoc;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelReference;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.contexts.ModelContext;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.ResolvedTypes.modelRefFactory;
import static springfox.documentation.spi.schema.contexts.ModelContext.inputParam;

/**
 * @author leegive
 *
 * 分页参数配置
 */
public class PageableParameterBuilderPlugin implements OperationBuilderPlugin {

    public static final String DEFAULT_PAGE_NAME = "page";
    public static final String PAGE_TYPE = "query";
    public static final String PAGE_DESCRIPTION = "Page number of the requested page";

    public static final String DEFAULT_SIZE_NAME = "size";
    public static final String SIZE_TYPE = "query";
    public static final String SIZE_DESCRIPTION = "Size of a page";

    public static final String DEFAULT_SORT_NAME = "sort";
    public static final String SORT_TYPE = "query";
    public static final String SORT_DESCRIPTION = "Sorting criteria in the format: property(,asc|desc). "
        + "Default sort order is ascending. "
        + "Multiple sort criteria are supported.";

    private final TypeNameExtractor nameExtractor;
    private final TypeResolver resolver;
    private final ResolvedType pageableType;

    public PageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
        this.nameExtractor = nameExtractor;
        this.resolver = resolver;
        this.pageableType = resolver.resolve(Pageable.class);
    }

    @Override
    public void apply(OperationContext context) {
        List<Parameter> parameters = newArrayList();
        for (ResolvedMethodParameter methodParameter : context.getParameters()) {
            ResolvedType resolvedType = methodParameter.getParameterType();

            if (pageableType.equals(resolvedType)) {
                ParameterContext parameterContext = new ParameterContext(methodParameter,
                    new ParameterBuilder(),
                    context.getDocumentationContext(),
                    context.getGenericsNamingStrategy(),
                    context);

                parameters.add(createPageParameter(parameterContext));
                parameters.add(createSizeParameter(parameterContext));
                parameters.add(createSortParameter(parameterContext));

                context.operationBuilder().parameters(parameters);
            }
        }
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return DocumentationType.SWAGGER_2.equals(delimiter);
    }

    protected String getPageName() {
        return DEFAULT_PAGE_NAME;
    }

    protected String getSizeName() {
        return DEFAULT_SIZE_NAME;
    }

    protected String getSortName() {
        return DEFAULT_SORT_NAME;
    }

    protected Parameter createPageParameter(ParameterContext context) {
        ModelReference intModel = createModelRefFactory(context).apply(resolver.resolve(Integer.TYPE));
        return new ParameterBuilder()
            .name(getPageName())
            .parameterType(PAGE_TYPE)
            .modelRef(intModel)
            .description(PAGE_DESCRIPTION)
            .build();
    }

    protected Parameter createSizeParameter(ParameterContext context) {
        ModelReference intModel = createModelRefFactory(context).apply(resolver.resolve(Integer.TYPE));
        return new ParameterBuilder()
            .name(getSizeName())
            .parameterType(SIZE_TYPE)
            .modelRef(intModel)
            .description(SIZE_DESCRIPTION)
            .build();
    }

    protected Parameter createSortParameter(ParameterContext context) {
        ModelReference stringModel = createModelRefFactory(context).apply(resolver.resolve(List.class, String.class));
        return new ParameterBuilder()
            .name(getSortName())
            .parameterType(SORT_TYPE)
            .modelRef(stringModel)
            .allowMultiple(true)
            .description(SORT_DESCRIPTION)
            .build();
    }

    protected Function<ResolvedType, ? extends ModelReference> createModelRefFactory(ParameterContext context) {
        ModelContext modelContext = inputParam(
            context.getGroupName(),
            context.resolvedMethodParameter().getParameterType(),
            context.getDocumentationType(),
            context.getAlternateTypeProvider(),
            context.getGenericNamingStrategy(),
            context.getIgnorableParameterTypes());
        return modelRefFactory(modelContext, nameExtractor);
    }

    TypeResolver getResolver() {
        return resolver;
    }

    TypeNameExtractor getNameExtractor() {
        return nameExtractor;
    }


}

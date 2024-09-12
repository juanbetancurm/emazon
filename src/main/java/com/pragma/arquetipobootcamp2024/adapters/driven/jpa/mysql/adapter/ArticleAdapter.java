package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IArticleEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.IArticleRepository;
import com.pragma.arquetipobootcamp2024.domain.model.ArticleModel;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ArticleAdapter implements IArticlePersistencePort{
    private static final Logger logger = LoggerFactory.getLogger(ArticleAdapter.class);
    private final IArticleRepository articleRepository;
    private final IArticleEntityMapper articleEntityMapper;

    public ArticleAdapter(IArticleRepository articleRepository, IArticleEntityMapper articleEntityMapper) {
        this.articleRepository = articleRepository;
        this.articleEntityMapper = articleEntityMapper;
    }

    @Override
    public ArticleModel save(ArticleModel articleModel) {
        // Map ArticleModel to ArticleEntity
        logger.info("Saving ArticleModel in persistence layer: {}", articleModel);
        ArticleEntity articleEntity = articleEntityMapper.toEntity(articleModel);

        // Save entity in the database
        logger.info("Mapped ArticleEntity: {}", articleEntity);
        ArticleEntity savedArticle = articleRepository.save(articleEntity);

        ArticleModel savedModel = articleEntityMapper.toModel(savedArticle);

        logger.info("Saved ArticleEntity and returning ArticleModel: {}", savedModel);

        return savedModel;
    }

}

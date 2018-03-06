package com.wechat.backend.repository;

import com.wechat.backend.domain.CollectList;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the CollectList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollectListRepository extends JpaRepository<CollectList, Long> {
    @Query("select distinct collect_list from CollectList collect_list left join fetch collect_list.collectProducts")
    List<CollectList> findAllWithEagerRelationships();

    @Query("select collect_list from CollectList collect_list left join fetch collect_list.collectProducts where collect_list.id =:id")
    CollectList findOneWithEagerRelationships(@Param("id") Long id);

    CollectList findByUserId(@Param("id") Long userId);
}

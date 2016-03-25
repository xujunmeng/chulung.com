package com.wck.blog.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wck.blog.bean.Article;
import com.wck.blog.dto.ArticleFiling;
import com.wck.blog.dto.Blog;
import com.wck.blog.dto.CommonInfo;
import com.wck.blog.service.BlogService;
import com.wck.cache.annotation.Cache;
import com.wck.durable.bean.Queryer;
import com.wck.util.NumberUtil;

@Service
public class BlogServiceImpl extends BaseService implements BlogService {
	private static final int PAGE_SIZE = 10;

	public List<Blog> findNewBlog(Optional<Integer> page, Integer typeId) {
		Article bean = new Article();
		bean.setTypeId(typeId);
		bean.setIsDelete(0);
		return convertToBlog(session
				.queryList(Queryer.of(bean).orderBy("CreateTime").desc().page(page.orElse(1)).pageSize(PAGE_SIZE)));
	}

	@Override
	public int getBlogPageCount(Integer typeId) {
		Article bean = new Article();
		bean.setTypeId(typeId);
		bean.setIsDelete(0);
		return (int) Math.ceil(this.session.count(Queryer.of(bean)) / Double.valueOf(PAGE_SIZE));
	}

	@Override
	public List<Blog> getBlogsByYearMonth(Integer year, Integer month) {
		if (NumberUtil.isRangeNotIn(year, 2014, 2993) && NumberUtil.isRangeNotIn(month, 1, 12)) {
			return Collections.emptyList();
		}
		LocalDate start = LocalDate.of(year, month, 1);
		LocalDate end = LocalDate.of(year, month, 1).plus(1, ChronoUnit.MONTHS);
		return convertToBlog(session.queryList("select * from Article where typeId=1 and createTime between ? and ?",
				Article.class, start, end));
	}

	private List<Blog> convertToBlog(List<Article> articles) {
		return articles.stream().map(a -> {
			return Blog.valueOf(a);
		}).collect(Collectors.toList());
	}

	@Override
	@Cache(key = "commonInfo", timeToLive = 30)
	public CommonInfo getCommonInfo() {
		// 归档信息
		List<ArticleFiling> list = new ArrayList<ArticleFiling>();
		Article bean = new Article();
		bean.setTypeId(1);
		session.queryList(Queryer.of(bean)).stream().parallel().map(article -> article.getCreateTime())
				.map(localDate -> YearMonth.of(localDate.getYear(), localDate.getMonthValue()))
				.collect(Collectors.groupingByConcurrent(yearMonth -> yearMonth, Collectors.counting()))
				.forEach((k, v) -> {
					list.add(new ArticleFiling(k, v.intValue()));
				});
		list.sort((o1, o2) -> {
			return o2.compareTo(o1);
		});
		return new CommonInfo(list);
	}
}

package com.chulung.blog.mapper;

import com.chulung.blog.model.Comments;
import com.chulung.ckbatis.mapper.BaseMapper;

public interface CommentsMapper extends BaseMapper<Comments>{
	/**
	 * 根据文章id更新评论数
	 * @param id
	 */
	public void recalcCommentsCountForArticle(Integer id);
}
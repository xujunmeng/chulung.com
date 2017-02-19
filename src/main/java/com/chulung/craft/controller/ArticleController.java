package com.chulung.craft.controller;

import com.chulung.craft.dto.JsonResult;
import com.chulung.craft.model.Article;
import com.chulung.craft.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 基于rest风格的文章,草稿WS
 * 
 * @author ChuKai
 *
 */
@RestController
@RequestMapping("/")
public class ArticleController extends BaseController {
	@Autowired
	private ArticleService articleService;

	/**
	 * 根据id查询文章
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getArticle(@PathVariable Integer id, HttpServletRequest request) {
		Article article = articleService.findArticleById(id);
		return modelAndView("article").addObject("article", article).addObject("typeId", article.getTypeId()).addObject("isLogin",webSessionSupport.islogIn());
	}

	@RequestMapping(value = "/article/relevancy/{id}")
	public  @ResponseBody JsonResult relevancy(@PathVariable Integer id){
		return JsonResult.ofSuccess(this.articleService.listRelevancy(id));
	}
}

<template>
  <div class="col-md-4" id="sidebar-div">
    <section class="widget widget_search transition-item" key="search">
      <form role="search" method="get" class="search-form" action="/search">
        <label>
          <span class="screen-reader-text">Search for:</span>
          <input class="search-field" placeholder="CSearch …" value="" name="word" title="Search for:"
                 type="search" required="required">
        </label>
        <input class="search-submit" value="Search" type="submit">
      </form><!-- search-form -->
    </section><!-- .widget_search -->
    <section class="widget danish_widget_about transition-item" key="about">
      <div class="about-author-container">
        <div class="about-author-info">
          <h2 class="widget-title">初龙(chulung)</h2>
          <span class="author-subtitle">Developer & Writer</span>
          <div class="author-description">
            <p>这是我的博客，不仅仅是技术博客，因为技术不再是我惟一的追求，也可以与你谈谈人生...</p>
          </div><!-- .author-description -->
          <span class="author-subtitle">微信公众号</span>
          <div class="author-description">
            <img src="/img/weixin.jpg"/>
            <router-link to="/article/about" class="more-link"><span class="moretext">More</span></router-link>
          </div><!-- .author-description -->
          <div class="author-footer">
            <div class="author-social">
              <a href="https://github.com/chulung" target="_blank" rel="external nofollow"><i
                class="fa fa-github"></i></a>
              <a href="mailto:chulung@chulung.com" rel="external nofollow" target="_blank"><i
                class="fa fa-envelope"></i></a>
              <a href="http://weibo.com/chulung" target="_blank" rel="external nofollow"><i
                class="fa fa-weibo"></i></a>
            </div><!-- .author-social -->
          </div><!-- .author-footer -->
        </div><!-- .about-author-info -->
      </div><!-- .about-author-container -->
    </section><!-- .danish_widget_about -->
    <section class="widget widget_archive transition-item" key="archive">
      <h2 class="widget-title">归档</h2>
      <select name="archive-dropdown" v-model="archiveDate">
        <router-link tag="option" to="/articles" value="">全部</router-link>
        <router-link tag="option"
                     :to="{path:'/articles',query:{year:item.yearMonth.year,month:item.yearMonth.monthValue}}"
                     v-for="item in archives" key="item">
          {{item.yearMonth.year + '-' + item.yearMonth.monthValue}}({{item.count}})
        </router-link>
      </select>
    </section><!-- .widget_archive -->
    <section class="widget widget_text transition-item" key="text">
      <h2 class="widget-title">常用网址</h2>
      <div class="textwidget">
        <ul>
          <li><a href="https://github.com/chulung" rel="external nofollow" target="_blank">GitHub</a></li>
          <li><a href="http://www.cnblogs.com/chulung/" rel="external nofollow" target="_blank">博客园</a></li>
        </ul>
      </div><!-- .textwidget -->
    </section>
    <section class="widget widget_recent_comments transition-item" key="comments">
      <h2 class="widget-title">最新评论</h2>
      <ul id="recentcomments">
        <li style="display: list-item;" v-for="item in recentlyComments">
          <span class="comment-author-link">{{item.userName}}</span> :
          <router-link :to="'/article/'+item.uri">{{item.comment}}
          </router-link>
        </li>
      </ul>
    </section><!-- .widget_recent_comments -->
  </div><!-- .col-md-4 -->
</template>
<script>
  import axios from '~plugins/axios'
  import lscache from 'lscache'
  export default{
    data () {
      return {
        'archives': [],
        'recentlyComments': [],
        'recommendedArticles': [],
        archiveDate: ''
      }
    },
    mounted () {
      if (process.BROWSER_BUILD && lscache.get('sidebarInfo')) {
        this.bindData(lscache.get('sidebarInfo'))
        return
      }
      axios.get('sidebarInfo').then((response) => {
        this.bindData(response.data)
        lscache.set('sidebarInfo', response.data, 10)
      })
    },
    methods: {
      bindData: function (data) {
        this.archives = data.archives
        this.recentlyComments = data.recentlyComments
        this.recommendedArticles = data.recommendedArticles
      },
      defaultPic: function (pic) {
        return pic || '/img/logo.jpg'
      }
    }
  }
</script>

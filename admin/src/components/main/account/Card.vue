<!--卡片管理 -->
<template>
    <div class="body-wrap">
      <Table border  :columns='cardColumns' :data='cardList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;'@on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :current="params.currentPage"  :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
    
</template>
<script>
export default {
  name: 'Card',
  data () {
    return {
        params:{
            pageSizeOpts:[10,20,50,100,500,1000],//每页条数切换的配置
            startNum:1,//初始化个数
            currentPage:1,//当前页
            pageNum:1,//获取的第几个开始
            pageSize:10,//每页的个数
            total:0//总数
        },
        //增加参数
			addCardModel:false,
			addLoading:false,
			addCardRules: {
                realname: [
                    {required: true, message: '姓名为必填项', trigger: 'blur'}
                    ]
                },
			addCard:{
			},
			//修改参数
			updateCardModel:false,
			updateLoading:false,
			updateCardRules: {
                realname: [
                    {required: true, message: '姓名为必填项', trigger: 'blur'}
                    ]
                },
			updateCard:{},
        //删除参数
        deleteCard:{},
	    cardList: [],
	    cardColumns: [
        {
          title: '序号',
          minWidth:100,
          align:'center',
          render: (h, params) => {
            return h('span', params.index
            +(this.params.currentPage-1)*this.params.pageSize+this.params.startNum);
          }
        },
        {
          title: '卡片id',
          key: 'cardId',
          minWidth:100,
          align:'center'
        },
         {
        	title:'商品id',
            key:'merId',
            minWidth:100,
            align:'center'
        },
         {
        	title:'账户id',
            key:'accountId',
            minWidth:100,
            align:'center'
        },
        {
        	title:'面料卡张数',
            key:'cardNumber1',
             minWidth:100,
            align:'center'
        },
        {
        	title:'五金卡张数',
            key:'cardNumber2',
            minWidth:100,
            align:'center'
        },
        {
        	title:'手挽卡张数',
            key:'cardNumber3',
            minWidth:100,
            align:'center'
        },
        {
        	title:'袋身卡张数',
            key:'cardNumber4',
            minWidth:100,
            align:'center'
        },
        {
          title:'创建时间',
          key:'createDate',
          minWidth:100,
          sortable: true,
          align:'center'
        },
        {
          title:'修改时间',
          key:'updateDate',
          minWidth:100,
          sortable: true,
          align:'center'
        },
		{
          title: '操作',
          key: 'action',
          minWidth:200,
          align:'center',
          fixed: 'right',
          render: (h, params) => {
            var varhh1=  h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginLeft: '10px'
                },
                on: {
                  click: () => {
                    this.update(params.row)
                  }
                }
              }, '编辑');
            var varhh2=  h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                style: {
                  marginLeft: '10px'
                },
                on: {
                  click: () => {
                    this.delete(params.row)
                  }
                }
              }, '删除');
            	var s=h("div","");
			s=h("div",[
              varhh1
              ,varhh2
            ]);
            return s;
          }
        }
      ],
    }
  },
  methods: {
    //分页点击
    selectPage (currentPage) {
      this.params.currentPage=currentPage;
      this.params.pageNum = (this.params.currentPage-1)*this.params.pageSize+this.params.startNum;
      //构造path
     let pp=JSON.stringify({
       currentPage:currentPage,
       accountId:JSON.parse(this.$route.params.pathParams).accountId,
       merId:JSON.parse(this.$route.params.pathParams).merId,
     })
      this.$router.push(this.$route.path.substr(0,this.$route.path.indexOf(this.$route.params.pathParams))+pp);
      this.getList()
    },
    //切换每页条数时的回调，返回切换后的每页条数
    onPageSizeChange(pageSize){
      this.getList(pageSize)
    },
  //获取列表
   getList (pageSize) {
     /**
     * 获取列表
     * $this  vue组件
     * p.countUrl 数量url
     * p.listUrl 列表url
     * p.list 返回列表
     */
    this.params.pageSize=pageSize||this.params.pageSize
    this.params.merId=JSON.parse(this.$route.params.pathParams).merId
    this.params.accountId=JSON.parse(this.$route.params.pathParams).accountId
     this.axiosbusiness.getList(this,{
       countUrl:'/card/count',
       listUrl:'/card/list',
       data:'cardList'
     },this.params)
    },
    //增加
	 add (params) {
      this.addCardModel = true
      this.addCard.accountId=JSON.parse(this.$route.params.pathParams).accountId
    },
		//增加取消
		 addCancel () {
      if (!this.addLoading) {
        this.addCardModel = false
        this.$refs.addCard.resetFields()
      }
    },
		//增加确定
    addSure () {
       /**
     * 增加
     * $this  vue组件
     * p.ref 验证
     * p.url 增加url
     * p.requestObject 请求参数对象
     * p.loading loading
     * p.showModel 界面模型显示隐藏
     */
    this.axiosbusiness.add(this,{
      ref:'addCard',
      url:'/card/add',
      requestObject:'addCard',
      loading:'addLoading',
      showModel:'addCardModel'
    })
    },
	 update (params) {
      this.updateCardModel = true
     //获取修改实体
      this.axiosbusiness.get(this,{
         url:'/card/load?cardId='+params.cardId,
         data:'updateCard',
       })
    },
		//修改取消
		 updateCancel () {
      if (!this.updateLoading) {
        this.updateCardModel = false
        this.$refs.updateCard.resetFields()
      }
    },
		//修改确定
    updateSure () {
      /**
     * 修改
     * $this  vue组件
     * p.ref 验证
     * p.url 修改url
     * p.requestObject 请求参数对象
     * p.loading loading
     * p.showModel 界面模型显示隐藏
     */
    this.axiosbusiness.update(this,{
      ref:'updateCard',
      url:'/card/update',
      requestObject:'updateCard',
      loading:'updateLoading',
      showModel:'updateCardModel'
    })
 
    },
    //删除
    delete(params){
    /**
     * 删除
     * $this  vue组件
     * p.url 修改url
     * p.requestObject 请求参数对象
     */
    this.deleteCard={
      "cardId":params.cardId
    };
    this.axiosbusiness.delete(this,{
      url:'/card/delete',
      requestObject:'deleteCard'
    })
    }
  },
   watch: {
    //当前页面参数修改动态启动
      $route (to,from){
        this.selectPage(JSON.parse(this.$route.params.pathParams).currentPage)
      }
    }, 
  created () {
     this.selectPage(JSON.parse(this.$route.params.pathParams).currentPage)
  },
  mounted () {

  }
}
</script>

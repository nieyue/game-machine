<!--行程管理 -->
<template>
    <div class="body-wrap">
    <div class="body-btn-wrap">
      <!-- <Button type='primary'  @click='add'>增加行程</Button> -->
    </div>
		 
      <Table border  height="500" :columns='tripColumns' :data='tripList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;' @on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :current="params.currentPage" :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
    
</template>
<script>
export default {
  name: 'Trip',
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
      isDoorList:[
        {id:1,value:'是'},
        {id:2,value:'否'},
      ],
			//增加参数
			addTripModel:false,
			addLoading:false,
			addTripRules: {
               /*  wxUuid: [
                    {required: true, message: '微信uuid为必填项', trigger: 'blur'}
                    ] */
                },
			addTrip:{
			},
			//修改参数
			updateTripModel:false,
			updateLoading:false,
			updateTripRules: {
               /*  wxUuid: [
                    {required: true, message: '微信uuid为必填项', trigger: 'blur'}
                    ] */
                },
			updateTrip:{},
      //删除参数
      deleteTrip:{},
	    TripList: [],
	    TripColumns: [
        {
          title: '序号',
          width:80,
          align:'center',
          render: (h, params) => {
            return h('span', params.index
            +(this.params.currentPage-1)*this.params.pageSize+this.params.startNum);
          }
        },
        {
          title: '行程id',
          key: 'tripId',
          minWidth:100,
          align:'center'
        },
        {
        	title:'出发地',
            key:'startAddress',
             minWidth:100,
            align:'center'
        },
        {
        	title:'目的地',
            key:'endAddress',
             minWidth:100,
            align:'center'
        },
        {
        	title:'出发时间',
            key:'startDate',
            minWidth:100,
            align:'center'
        },
         {
        	title:'是否上门接送',
            key:'status',
             minWidth:100,
          align:'center',
          render: (h, params) => {
            let isDoorvalue="";
            this.isDoorList.forEach(element => {
              if(element.id==params.row.isDoor){
                isDoorvalue=element.value;
              }
            });
             return  h('span',isDoorvalue);
          }
        },
        {
        	title:'金额/人',
            key:'unitPrice',
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
             // ,varhh2
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
     this.params.accountId=JSON.parse(this.$route.params.pathParams).accountId
     this.axiosbusiness.getList(this,{
       countUrl:'/trip/count',
       listUrl:'/trip/list',
       data:'tripList'
     },this.params)
    },
  //增加
	 add (params) {
      this.addTripModel = true
      this.addTrip.accountId=JSON.parse(this.$route.params.pathParams).accountId
    },
		//增加取消
		 addCancel () {
      if (!this.addLoading) {
        this.addTripModel = false
        this.$refs.addTrip.resetFields()
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
      ref:'addTrip',
      url:'/trip/add',
      requestObject:'addTrip',
      loading:'addLoading',
      showModel:'addTripModel'
    })
    },
	 update (params) {
      this.updateTripModel = true
     //获取修改实体
      this.axiosbusiness.get(this,{
         url:'/trip/load?tripId='+params.tripId,
         data:'updateTrip',
       })
    },
		//修改取消
		 updateCancel () {
      if (!this.updateLoading) {
        this.updateTripModel = false
        this.$refs.updateTrip.resetFields()
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
      ref:'updateTrip',
      url:'/trip/update',
      requestObject:'updateTrip',
      loading:'updateLoading',
      showModel:'updateTripModel'
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
    this.deleteTrip={
      "tripId":params.tripId
    };
    this.axiosbusiness.delete(this,{
      url:'/trip/delete',
      requestObject:'deleteTrip'
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

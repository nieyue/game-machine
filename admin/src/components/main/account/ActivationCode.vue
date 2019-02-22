<!--激活码管理 -->
<template>
    <div class="body-wrap">
        <div class="body-btn-wrap">
          <Button type='primary'  @click='add'>批量增加</Button>
          <Button type='info' style="margin:0 20px;" @click='exportActivationCode'>激活码导出</Button>
          <div class="search-wrap">
            <DatePicker type="date" placeholder="查询日期"  format="yyyy-MM-dd"
                @on-change="getParamsCreateDate" style="width: 150px" ></DatePicker>
            <Select v-model="params.isUsered" transfer class="search-wrap-input"  placeholder="是否使用，全部">
                <Option v-for="item in isUseredParamsList" :value="item.id" :key="item.id">{{ item.value }}</Option>
            </Select>
            <Button @click="search" type="info"  >查询</Button>
          </div>
        </div>
          <Modal v-model="addActivationCodeModel"
           title="修改密码"
           :closable="false"
           :mask-closable="false"
    >
    <Form ref="addActivationCode" :model="addActivationCode" :label-width="100" label-position="right"  :rules="addActivationCodeRules">
        <FormItem prop="number" label="数目:">
          <InputNumber   :max="10000000" :min="1" :step="1" style="width:180px" v-model="addActivationCode.number"
            placeholder="数目">
          </InputNumber >
        </FormItem>
      </Form>
      <div slot='footer'>
        <Button  @click='addActivationCodeCancel'>取消</Button>
        <Button type='primary' :loading='addActivationCodeLoading'>
          <span v-if="!addActivationCodeLoading" @click='addActivationCodeSure'>确定</span>
          <span v-else>Loading...</span>
        </Button>
      </div>
    </Modal>

      <Table border height="500"  :columns='activationCodeColumns' :data='activationCodeList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;'  @on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :current="params.currentPage" :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
    
</template>
<script>
export default {
  name: 'ActivationCode',
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
        //是否使用，1未使用，2已使用
      isUseredList:[
        {id:1,value:'未使用'},
        {id:2,value:'已使用'}
        ],
        //是否使用，1未使用，2已使用
      isUseredParamsList:[
        {id:'',value:'全部'},
        {id:1,value:'未使用'},
        {id:2,value:'已使用'}
        ],
        //增加激活码
			addActivationCodeModel:false,
			addActivationCodeLoading:false,
			addActivationCodeRules: {
                number: [
                    {type:'number',required: true, message: '数目必填', trigger: 'change'}
                    ],
                },
			addActivationCode:{ 
                number:1000
            },
	    activationCodeList: [],
	    activationCodeColumns: [
        {
          type: 'selection',
          align: 'center'
        },
        {
          title: '序号',
          align:'center',
          render: (h, params) => {
            return h('span', params.index
            +(this.params.currentPage-1)*this.params.pageSize+this.params.startNum);
          }
        },
        {
          title: '激活码id',
          key: 'activationCodeId',
          align:'center'
        },
        {
        	title:'激活码',
            key:'code',
            align:'center'
        },
         {
          title:'是否使用',
          minWidth:100,
            key:'isUsered',
          align:'center',
          render: (h, params) => {
              
            let isUseredvalue="";
            this.isUseredList.forEach(element => {
              if(element.id==params.row.isUsered){
                isUseredvalue=element.value;
              }
            });
             return  h('span',isUseredvalue);
          }
        },
        {
          title:'创建时间',
          key:'createDate',
          sortable: true,
          align:'center'
        },
        {
          title:'修改时间',
          key:'updateDate',
          sortable: true,
          align:'center'
        }
      ],
    }
  },
  methods: {
     //查询
    search(){
      this.params.currentPage=1;
      this.params.pageNum =1;
      this.getList()
    },
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
    //获取查询日期
    getParamsCreateDate(d){
      this.params.createDate=d+" 00:00:00";
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
       countUrl:'/activationCode/count',
       listUrl:'/activationCode/list',
       data:'activationCodeList'
     },this.params)
    },
    ////增加激活码
        add (params) {
      this.addActivationCodeModel = true

    },
		//取消
		 addActivationCodeCancel () {
      if (!this.addActivationCodeLoading) {
        this.addActivationCodeModel = false
        this.$refs.addActivationCode.resetFields()
      }
    },
		//确定
    addActivationCodeSure () {
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
      ref:'addActivationCode',
      url:'/activationCode/addBatch',
      requestObject:'addActivationCode',
      loading:'addActivationCodeLoading',
      showModel:'addActivationCodeModel'
    })

    },
    //导出
    exportActivationCode(){
      var als=this.$refs.table.getSelection();
      if(als.length<=0){
        this.$Message.error("最少选一个")
        return;
      }
      als.forEach(e=>{
        if(e.isUsered==1){
          e.isUsered='未使用'
        }else{
          e.isUsered='已使用'
        }
      })
      this.$refs.table.exportCsv({
          filename: '激活码数据',
          columns: this.activationCodeColumns.filter((data, index) => index>=2),
          data: als
      });
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
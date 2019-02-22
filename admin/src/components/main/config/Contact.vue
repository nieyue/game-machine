<!--联系管理 -->
<template>
    <div class="body-wrap">
    <div class="body-btn-wrap">
       <Button type='primary'  @click='add'>增加联系</Button>
    </div>
		 <!--新增 -->
     <Modal v-model="addContactModel"
           title="新增联系管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="addContact" :model="addContact" :label-width="100" label-position="right"  :rules="addContactRules">
        <FormItem prop="address" label="联系地址:">
          <Input type="text" v-model="addContact.address" placeholder="联系地址">
          </Input>
        </FormItem>
      </Form>
      <div slot='footer'>
        <Button  @click='addCancel'>取消</Button>
        <Button type='primary' :loading='addLoading'>
          <span v-if="!addLoading" @click='addSure'>确定</span>
          <span v-else>Loading...</span>
        </Button>
      </div>
    </Modal>
    <!--新增end -->
		 <!--修改 -->
     <Modal v-model="updateContactModel"
           title="修改联系管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="updateContact" :model="updateContact" :label-width="100" label-position="right"  :rules="updateContactRules">
        <FormItem prop="address" label="联系地址:">
          <Input type="text" v-model="updateContact.address" placeholder="联系地址">
          </Input>
        </FormItem>
      </Form>
      <div slot='footer'>
        <Button  @click='updateCancel'>取消</Button>
        <Button type='primary' :loading='updateLoading'>
          <span v-if="!updateLoading" @click='updateSure'>确定</span>
          <span v-else>Loading...</span>
        </Button>
      </div>
    </Modal>
    <!--修改end -->
      <Table border height="500" :columns='contactColumns' :data='contactList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;'  @on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :current="params.currentPage" :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
</template>
<script>
export default {
  name: 'Contact',
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
			addContactModel:false,
			addLoading:false,
			addContactRules: {
                address: [
                    {required: true, message: '联系地址为必填项', trigger: 'blur'}
                    ]
                },
			addContact:{
			},
			//修改参数
			updateContactModel:false,
			updateLoading:false,
			updateContactRules: {
                address: [
                    {required: true, message: '联系地址为必填项', trigger: 'blur'}
                    ]
                },
			updateContact:{
    		 "contactId":1,
    		 "address":""
      },
      //删除参数
      deleteContact:{},
	    contactList: [],
	    contactColumns: [
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
          title: '联系id',
          minWidth:100,
          key: 'contactId',
          align:'center'
        },
        {
          title:'联系地址',
          minWidth:100,
        	key:'address',
          align:'center'
        },
        {
          title:'创建时间',
          minWidth:100,
          key:'createDate',
          sortable: true,
          align:'center'
        },
        {
          title:'修改时间',
          minWidth:100,
          key:'updateDate',
          sortable: true,
          align:'center'
        },
				{
          title: '操作',
          minWidth:200,
          key: 'action',
          align:'center',
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
              varhh1,
              varhh2
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
       accountId:JSON.parse(this.$route.params.pathParams).accountId
     })
     //console.log(this.$route.path.substr(0,this.$route.path.indexOf(this.$route.params.pathParams)))
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
     * p.data 返回列表
     */
    this.params.pageSize=pageSize||this.params.pageSize
     this.axiosbusiness.getList(this,{
       countUrl:'/contact/count',
       listUrl:'/contact/list',
       data:'contactList'
     },this.params)
    },
  //增加
	 add (params) {
      this.addContactModel = true
      this.addContact.name = params.name
    },
		//增加取消
		 addCancel () {
      if (!this.addLoading) {
        this.addContactModel = false
        this.$refs.addContact.resetFields()
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
      ref:'addContact',
      url:'/contact/add',
      requestObject:'addContact',
      loading:'addLoading',
      showModel:'addContactModel'
    })
    },
	 update (params) {
      this.updateContactModel = true
      //获取修改实体
      this.axiosbusiness.get(this,{
         url:'/contact/load?contactId='+params.contactId,
         data:'updateContact',
       })
    },
		//修改取消
		 updateCancel () {
      if (!this.updateLoading) {
        this.updateContactModel = false
        this.$refs.updateContact.resetFields()
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
      ref:'updateContact',
      url:'/contact/update',
      requestObject:'updateContact',
      loading:'updateLoading',
      showModel:'updateContactModel'
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
    this.deleteContact={
      "contactId":params.contactId
    };
    this.axiosbusiness.delete(this,{
      url:'/contact/delete',
      requestObject:'deleteContact'
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
    //this.getList();
  },
  mounted () {

  }
}
</script>

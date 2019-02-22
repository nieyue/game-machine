<!--地址管理 -->
<template>
    <div class="body-wrap">
    <div class="body-btn-wrap">
        <Button type='primary'  @click='add'>增加地址</Button>
          <Upload style="display:inline-block;"
          name="excel"
          :show-upload-list="false"
          :action="upload.action"
          :on-progress="onProgess"
          :on-success="handleSuccess">
          <Button icon="ios-cloud-upload-outline">导入地址</Button>
          </Upload>
         <div class="search-wrap">
          <Input v-model="params.city" class="search-wrap-input" placeholder="城市"></Input>
          <Button @click="search" type="info"  >查询</Button>
        </div>
    </div>
		 <!--新增 -->
     <Modal v-model="addAddressModel"
           title="新增地址管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="addAddress" :model="addAddress" :label-width="100" label-position="right"  :rules="addAddressRules">
          <FormItem prop="type" label="类型:">
            <Select v-model="addAddress.type" transfer size="large" style="width:100px">
                <Option v-for="item in adList" :value="item.id" :key="item.id">{{ item.value }}</Option>
            </Select>
        </FormItem>
        <FormItem prop="city" label="城市:">
          <Input type="text" v-model="addAddress.city" placeholder="城市">
          </Input>
        </FormItem>
        <FormItem prop="address" label="地址:">
          <Input type="text" v-model="addAddress.address" placeholder="地址">
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
     <Modal v-model="updateAddressModel"
           title="修改地址管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="updateAddress" :model="updateAddress" :label-width="100" label-position="right"  :rules="updateAddressRules">
        <FormItem prop="type" label="类型:">
            <Select v-model="updateAddress.type" transfer size="large" style="width:100px">
                <Option v-for="item in adList" :value="item.id" :key="item.id">{{ item.value }}</Option>
            </Select>
        </FormItem>
        <FormItem prop="city" label="城市:">
          <Input type="text" v-model="updateAddress.city" placeholder="城市">
          </Input>
        </FormItem>
        <FormItem prop="address" label="地址:">
          <Input type="text" v-model="updateAddress.address" placeholder="地址">
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
      <Table border height="500" :columns='addressColumns' :data='addressList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;'  @on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :current="params.currentPage" :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
</template>
<script>
export default {
  name: 'Address',
  data () {
    return {
      //上传
      upload:{
        action:this.axios.defaults.imgURL+"/address/importExcel",
      },
        params:{
            pageSizeOpts:[10,20,50,100,500,1000],//每页条数切换的配置
            startNum:1,//初始化个数
            currentPage:1,//当前页
            pageNum:1,//获取的第几个开始
            pageSize:10,//每页的个数
            total:0//总数
        },
        //类型，1出发地，2目的地
        adList:[
            {id:1,value:'出发地'},
            {id:2,value:'目的地'},
        ],
			//增加参数
			addAddressModel:false,
			addLoading:false,
			addAddressRules: {
                city: [
                    {required: true, message: '城市为必填项', trigger: 'blur'}
                    ],
                address: [
                    {required: true, message: '地址为必填项', trigger: 'blur'}
                    ]
                },
			addAddress:{
        type:1
			},
			//修改参数
			updateAddressModel:false,
			updateLoading:false,
			updateAddressRules: {
                city: [
                    {required: true, message: '城市为必填项', trigger: 'blur'}
                    ],
                address: [
                    {required: true, message: '地址为必填项', trigger: 'blur'}
                    ]
                },
			updateAddress:{
    		 "addressId":1,
    		 "address":""
      },
      //删除参数
      deleteAddress:{},
	    addressList: [],
	    addressColumns: [
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
          title: '地址id',
          minWidth:100,
          key: 'addressId',
          align:'center'
        },
       {
          title:'类型',
          minWidth:100,
            key:'type',
          align:'center',
          render: (h, params) => {
              
            let typevalue="";
            this.adList.forEach(element => {
              if(element.id==params.row.type){
                typevalue=element.value;
              }
            });
             return  h('span',typevalue);
          }
        },
        {
          title:'城市',
          minWidth:100,
        	key:'city',
          align:'center'
        },
        {
          title:'地址',
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
    //上传时
    onProgess(event, file, fileList){
       this.$Spin.show();
    },
    //上传成功
        handleSuccess (res, file){
          if(res.code==200){
            this.getList()
          }
             this.$Spin.hide();
            //console.log(res)
          // console.log(file)
        },
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
       countUrl:'/address/count',
       listUrl:'/address/list',
       data:'addressList'
     },this.params)
    },
  //增加
	 add (params) {
      this.addAddressModel = true
      this.addAddress.name = params.name
    },
		//增加取消
		 addCancel () {
      if (!this.addLoading) {
        this.addAddressModel = false
        this.$refs.addAddress.resetFields()
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
      ref:'addAddress',
      url:'/address/add',
      requestObject:'addAddress',
      loading:'addLoading',
      showModel:'addAddressModel'
    })
    },
	 update (params) {
      this.updateAddressModel = true
      //获取修改实体
      this.axiosbusiness.get(this,{
         url:'/address/load?addressId='+params.addressId,
         data:'updateAddress',
       })
    },
		//修改取消
		 updateCancel () {
      if (!this.updateLoading) {
        this.updateAddressModel = false
        this.$refs.updateAddress.resetFields()
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
      ref:'updateAddress',
      url:'/address/update',
      requestObject:'updateAddress',
      loading:'updateLoading',
      showModel:'updateAddressModel'
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
    this.deleteAddress={
      "addressId":params.addressId
    };
    this.axiosbusiness.delete(this,{
      url:'/address/delete',
      requestObject:'deleteAddress'
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

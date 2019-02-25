<!--商品管理 -->
<template>
    <div class="body-wrap">
    <div class="body-btn-wrap">
       <Button type='primary'  @click='add'>增加商品</Button>
    </div>
		 <!--新增 -->
     <Modal v-model="addMerModel"
           title="新增商品管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="addMer" :model="addMer" :label-width="100" label-position="right"  :rules="addMerRules">
        <FormItem prop="name" label="名称:">
          <Input type="text" v-model="addMer.name" placeholder="名称">
          </Input>
        </FormItem>
        <FormItem prop="imgAddress" label="封面(上传或者填写):">
            <my-upload :defaultUpload="addMer.imgAddress" @uploadList="getAddImgAddress"></my-upload>
            <div>
              <Input type="text" v-model="addMer.imgAddress" placeholder="封面">
            </Input>
              <img :src="addMer.imgAddress"  style='width:30px;'alt="">
            </div>
        </FormItem>
         <FormItem prop="status" label="状态:">
          <Select v-model="addMer.status" transfer size="large" style="width:100px">
              <Option v-for="item in statusList" :value="item.id" :key="item.id">{{ item.value }}</Option>
          </Select>
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
     <Modal v-model="updateMerModel"
           title="修改商品管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="updateMer" :model="updateMer" :label-width="100" label-position="right"  :rules="updateMerRules">
        <FormItem prop="name" label="名称:">
          <Input type="text" v-model="updateMer.name" placeholder="名称">
          </Input>
        </FormItem>
        <FormItem prop="imgAddress" label="封面(上传或者填写):">
            <my-upload :defaultUpload="updateMer.imgAddress" @uploadList="getUpdateImgAddress"></my-upload>
            <div>
              <Input type="text" v-model="updateMer.imgAddress" placeholder="封面">
            </Input>
              <img :src="updateMer.imgAddress"  style='width:30px;'alt="">
            </div>
        </FormItem>
         <FormItem prop="status" label="状态:">
          <Select v-model="updateMer.status" transfer size="large" style="width:100px">
              <Option v-for="item in statusList" :value="item.id" :key="item.id">{{ item.value }}</Option>
          </Select>
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
      <Table border height="500" :columns='merColumns' :data='merList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;'  @on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :current="params.currentPage" :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
</template>
<script>
export default {
  name: 'Mer',
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
        //状态
      statusList:[
        {id:1,value:'上架'},
        {id:2,value:'下架'}
        ],
			//增加参数
			addMerModel:false,
			addLoading:false,
			addMerRules: {
                name: [
                    {required: true, message: '商品名称为必填项', trigger: 'blur'}
                    ]
                },
			addMer:{
                status:1,
                imgAddress:''
			},
			//修改参数
			updateMerModel:false,
			updateLoading:false,
			updateMerRules: {
                name: [
                    {required: true, message: '商品名称为必填项', trigger: 'blur'}
                    ]
                },
			updateMer:{
    		 "merId":1,
      },
      //删除参数
      deleteMer:{},
	    merList: [],
	    merColumns: [
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
          title: '商品id',
          minWidth:100,
          key: 'merId',
          align:'center'
        },
        {
          title:'商品名称',
          minWidth:100,
        	key:'name',
          align:'center'
        },
        {
        	title:'封面',
            key:'imgAddress',
            minWidth:100,
          align:'center',
          render: (h, params) => {
            return h('img', {
              attrs: {
                src: params.row.imgAddress
              },
              style: {
                width: '80px'
              }
            })
          }
        },
        {
        	title:'状态',
            key:'status',
             minWidth:100,
          align:'center',
          render: (h, params) => {
            let statusvalue="";
            this.statusList.forEach(element => {
              if(element.id==params.row.status){
                statusvalue=element.value;
              }
            });
             return  h('span',statusvalue);
          }
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
     })
      this.$router.push(this.$route.path.substr(0,this.$route.path.indexOf(this.$route.params.pathParams))+pp);
      this.getList()
    },
    //切换每页条数时的回调，返回切换后的每页条数
    onPageSizeChange(pageSize){
      this.getList(pageSize)
    },
    //获取增加的图片
    getAddImgAddress(data){
      this.addMer.imgAddress=data[0].url
    },
    //获取修改的图片
    getUpdateImgAddress(data){
      this.updateMer.imgAddress=data[0].url
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
       countUrl:'/mer/count',
       listUrl:'/mer/list',
       data:'merList'
     },this.params)
    },
  //增加
	 add (params) {
      this.addMerModel = true
      this.addMer.name = params.name
    },
		//增加取消
		 addCancel () {
      if (!this.addLoading) {
        this.addMerModel = false
        this.$refs.addMer.resetFields()
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
      ref:'addMer',
      url:'/mer/add',
      requestObject:'addMer',
      loading:'addLoading',
      showModel:'addMerModel'
    })
    },
	 update (params) {
      this.updateMerModel = true
      //获取修改实体
      this.axiosbusiness.get(this,{
         url:'/mer/load?merId='+params.merId,
         data:'updateMer',
       })
    },
		//修改取消
		 updateCancel () {
      if (!this.updateLoading) {
        this.updateMerModel = false
        this.$refs.updateMer.resetFields()
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
      ref:'updateMer',
      url:'/mer/update',
      requestObject:'updateMer',
      loading:'updateLoading',
      showModel:'updateMerModel'
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
    this.deleteMer={
      "merId":params.merId
    };
    this.axiosbusiness.delete(this,{
      url:'/mer/delete',
      requestObject:'deleteMer'
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

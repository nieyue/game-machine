<!--充值项管理 -->
<template>
    <div class="body-wrap">
    <div class="body-btn-wrap">
       <Button type='primary'  @click='add'>增加充值项</Button>
    </div>
		 <!--新增 -->
     <Modal v-model="addRechargeTermModel"
           title="新增充值项管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="addRechargeTerm" :model="addRechargeTerm" :label-width="100" label-position="right"  :rules="addRechargeTermRules">
        <FormItem prop="title" label="附加标题:">
          <Input type="text" v-model="addRechargeTerm.title" placeholder="附加标题">
          </Input>
        </FormItem>
        <FormItem prop="rechargeMoney" label="充值真钱:">
          <InputNumber   :max="10000000" :min="0" :step="1" style="width:180px" v-model="addRechargeTerm.rechargeMoney"
            placeholder="充值真钱">
          </InputNumber >
        </FormItem>
        <FormItem prop="giveNumber" label="赠送次数:">
          <InputNumber   :max="10000000" :min="0" :step="1" style="width:180px" v-model="addRechargeTerm.giveNumber"
            placeholder="赠送次数">
          </InputNumber >
        </FormItem>
         <FormItem prop="status" label="状态:">
          <Select v-model="addRechargeTerm.status" transfer size="large" style="width:100px">
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
     <Modal v-model="updateRechargeTermModel"
           title="修改充值项管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="updateRechargeTerm" :model="updateRechargeTerm" :label-width="100" label-position="right"  :rules="updateRechargeTermRules">
        <FormItem prop="title" label="附加标题:">
          <Input type="text" v-model="updateRechargeTerm.title" placeholder="附加标题">
          </Input>
        </FormItem>
        <FormItem prop="rechargeMoney" label="充值真钱:">
          <InputNumber   :max="10000000" :min="0" :step="1" style="width:180px" v-model="updateRechargeTerm.rechargeMoney"
            placeholder="充值真钱">
          </InputNumber >
        </FormItem>
        <FormItem prop="giveNumber" label="赠送次数:">
          <InputNumber   :max="10000000" :min="0" :step="1" style="width:180px" v-model="updateRechargeTerm.giveNumber"
            placeholder="赠送次数">
          </InputNumber >
        </FormItem>
         <FormItem prop="status" label="状态:">
          <Select v-model="updateRechargeTerm.status" transfer size="large" style="width:100px">
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
      <Table border height="500" :columns='rechargeTermColumns' :data='rechargeTermList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;'  @on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :current="params.currentPage" :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
</template>
<script>
export default {
  name: 'RechargeTerm',
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
			addRechargeTermModel:false,
			addLoading:false,
			addRechargeTermRules: {
                rechargeMoney: [
                    {type:"number",required: true, message: '充值真钱为必填项', trigger: 'change'}
                    ]
                },
			addRechargeTerm:{
                status:1,
                rechargeMoney:0,
                giveNumber:0,
			},
			//修改参数
			updateRechargeTermModel:false,
			updateLoading:false,
			updateRechargeTermRules: {
                rechargeMoney: [
                    {type:"number",required: true, message: '充值真钱为必填项', trigger: 'change'}
                    ]
                },
			updateRechargeTerm:{
    		 "rechargeTermId":1,
      },
      //删除参数
      deleteRechargeTerm:{},
	    rechargeTermList: [],
	    rechargeTermColumns: [
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
          title: '充值项id',
          minWidth:100,
          key: 'rechargeTermId',
          align:'center'
        },
        {
          title:'附加标题',
          minWidth:100,
        	key:'title',
          align:'center'
        },
        {
          title:'充值真钱',
          minWidth:100,
        	key:'rechargeMoney',
          align:'center'
        },
        {
          title:'赠送次数',
          minWidth:100,
        	key:'giveNumber',
          align:'center'
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
       countUrl:'/rechargeTerm/count',
       listUrl:'/rechargeTerm/list',
       data:'rechargeTermList'
     },this.params)
    },
  //增加
	 add (params) {
      this.addRechargeTermModel = true
      this.addRechargeTerm.name = params.name
    },
		//增加取消
		 addCancel () {
      if (!this.addLoading) {
        this.addRechargeTermModel = false
        this.$refs.addRechargeTerm.resetFields()
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
      ref:'addRechargeTerm',
      url:'/rechargeTerm/add',
      requestObject:'addRechargeTerm',
      loading:'addLoading',
      showModel:'addRechargeTermModel'
    })
    },
	 update (params) {
      this.updateRechargeTermModel = true
      //获取修改实体
      this.axiosbusiness.get(this,{
         url:'/rechargeTerm/load?rechargeTermId='+params.rechargeTermId,
         data:'updateRechargeTerm',
       })
    },
		//修改取消
		 updateCancel () {
      if (!this.updateLoading) {
        this.updateRechargeTermModel = false
        this.$refs.updateRechargeTerm.resetFields()
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
      ref:'updateRechargeTerm',
      url:'/rechargeTerm/update',
      requestObject:'updateRechargeTerm',
      loading:'updateLoading',
      showModel:'updateRechargeTermModel'
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
    this.deleteRechargeTerm={
      "rechargeTermId":params.rechargeTermId
    };
    this.axiosbusiness.delete(this,{
      url:'/rechargeTerm/delete',
      requestObject:'deleteRechargeTerm'
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

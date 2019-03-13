<!--广告管理 -->
<template>
    <div class="body-wrap">
    <div class="body-btn-wrap">
       <Button type='primary'  @click='add'>增加广告</Button>
    </div>
		 <!--新增 -->
     <Modal v-model="addAdModel"
           title="新增广告管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="addAd" :model="addAd" :label-width="100" label-position="right"  :rules="addAdRules">
        <FormItem prop="name" label="名称:">
          <Input type="text" v-model="addAd.name" placeholder="名称">
          </Input>
        </FormItem>
        <FormItem prop="orderName" label="单号:">
          <Input type="text" v-model="addAd.orderName" placeholder="单号">
          </Input>
        </FormItem>
        <FormItem prop="typeName" label="分类:">
          <Input type="text" v-model="addAd.typeName" placeholder="分类">
          </Input>
        </FormItem>
        <FormItem prop="link" label="图文链接:">
          <Input type="text" v-model="addAd.link" placeholder="图文链接">
          </Input>
        </FormItem>
        <FormItem prop="title" label="广告标题:">
          <Input type="text" v-model="addAd.title" placeholder="广告标题">
          </Input>
        </FormItem>
        <FormItem prop="content" label="广告内容:">
          <Input type="text" v-model="addAd.content" placeholder="广告内容">
          </Input>
        </FormItem>
        <FormItem prop="startDate" label="开始时间:">
          <Input type="text" v-model="addAd.startDate" placeholder="开始时间">
          </Input>
        </FormItem>
        <FormItem prop="sendDate" label="发送时段:">
          <Input type="text" v-model="addAd.sendDate" placeholder="发送时段">
          </Input>
        </FormItem>
        <FormItem prop="sendSpeed" label="发送速度:">
          <Input type="text" v-model="addAd.sendSpeed" placeholder="发送速度">
          </Input>
        </FormItem>
        <FormItem prop="sendNumber" label="计划发送量:">
          <Input type="text" v-model="addAd.sendNumber" placeholder="计划发送量">
          </Input>
        </FormItem>
        <FormItem prop="finishSendNumber" label="已完成发送量:">
          <Input type="text" v-model="addAd.finishSendNumber" placeholder="已完成发送量">
          </Input>
        </FormItem>
        <FormItem prop="status" label="广告状态:">
          <Input type="text" v-model="addAd.status" placeholder="广告状态">
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
     <Modal v-model="updateAdModel"
           title="修改广告管理"
           :closable="false"
           :mask-closable="false"
    >
      <Form ref="updateAd" :model="updateAd" :label-width="100" label-position="right"  :rules="updateAdRules">
        <FormItem prop="name" label="名称:">
          <Input type="text" v-model="updateAd.name" placeholder="名称">
          </Input>
        </FormItem>
        <FormItem prop="orderName" label="单号:">
          <Input type="text" v-model="updateAd.orderName" placeholder="单号">
          </Input>
        </FormItem>
        <FormItem prop="typeName" label="分类:">
          <Input type="text" v-model="updateAd.typeName" placeholder="分类">
          </Input>
        </FormItem>
        <FormItem prop="link" label="图文链接:">
          <Input type="text" v-model="updateAd.link" placeholder="图文链接">
          </Input>
        </FormItem>
        <FormItem prop="title" label="广告标题:">
          <Input type="text" v-model="updateAd.title" placeholder="广告标题">
          </Input>
        </FormItem>
        <FormItem prop="content" label="广告内容:">
          <Input type="text" v-model="updateAd.content" placeholder="广告内容">
          </Input>
        </FormItem>
        <FormItem prop="startDate" label="开始时间:">
          <Input type="text" v-model="updateAd.startDate" placeholder="开始时间">
          </Input>
        </FormItem>
        <FormItem prop="sendDate" label="发送时段:">
          <Input type="text" v-model="updateAd.sendDate" placeholder="发送时段">
          </Input>
        </FormItem>
        <FormItem prop="sendSpeed" label="发送速度:">
          <Input type="text" v-model="updateAd.sendSpeed" placeholder="发送速度">
          </Input>
        </FormItem>
        <FormItem prop="sendNumber" label="计划发送量:">
          <Input type="text" v-model="updateAd.sendNumber" placeholder="计划发送量">
          </Input>
        </FormItem>
        <FormItem prop="finishSendNumber" label="已完成发送量:">
          <Input type="text" v-model="updateAd.finishSendNumber" placeholder="已完成发送量">
          </Input>
        </FormItem>
        <FormItem prop="status" label="广告状态:">
          <Input type="text" v-model="updateAd.status" placeholder="广告状态">
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
      <Table border height="500" :columns='adColumns' :data='adList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;'  @on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :current="params.currentPage" :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
</template>
<script>
export default {
  name: 'Ad',
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
			addAdModel:false,
			addLoading:false,
			addAdRules: {
                },
			addAd:{
                status:1,
                rechargeMoney:0,
                giveNumber:0,
			},
			//修改参数
			updateAdModel:false,
			updateLoading:false,
			updateAdRules: {
                },
			updateAd:{
    		 "adId":1,
      },
      //删除参数
      deleteAd:{},
	    adList: [],
	    adColumns: [
        {
          title: '序号',
          minWidth:100,
          align:'center',
          render: (h, params) => {
            return h('span', params.index
            +(this.params.currentPage-1)*this.params.pageSize+this.params.startNum);
          }
        },
       /*  {
          title: '广告id',
          minWidth:100,
          key: 'adId',
          align:'center'
        }, */
        {
          title:'广告信息',
          minWidth:100,
        	key:'name',
          align:'left',
          render: (h, params) => {
             var orderName=h('div', "单号:"+params.row.orderName);
             var name=h('div', "名称:"+params.row.name);
             var typeName=h('div', "分类:"+params.row.typeName);
             var link=h('a', {
              attrs: {
                href: params.row.link
              },
              style: {
                backgroundColor:'green',
                padding:'5px',
                color:'#fff',
              }
            },"图文链接");
             return  h('div',[
                 orderName,
                 name,
                 typeName,
                 link,
             ]);
          }
        },
        {
          title:'广告内容',
          minWidth:100,
        	key:'title',
          align:'left',
          render: (h, params) => {
              var title=h('div', params.row.title);
              var content=h('div', params.row.content);
             return  h('div',[
                    title,
                    content,
             ]);
          }
        },
        {
          title:'开始时间/发送时段/发送速度',
          /* children:[
              {
                  title:'发送时段',
                  minWidth:100,
                  align:'center',
              children:[
                {
                    title:'发送速度',
                    minWidth:100,
                    align:'center',
                },

              ]
              },
          ], */
          minWidth:100,
        	key:'startDate',
          align:'left',
          render: (h, params) => {
              var startDate=h('div', "开始:"+params.row.startDate);
              var sendDate=h('div', "时段:"+params.row.sendDate);
              var sendSpeed=h('div', "速度:"+params.row.sendSpeed);
             return  h('div',[
                    startDate,
                    sendDate,
                    sendSpeed,
             ]);
          }
        },
        {
          title:'计划发送量/已完成发送量',
         /*  children:[
              {
                  title:'已完成发送量',
                  minWidth:100,
                  align:'center',
              },
          ], */
          minWidth:100,
        	key:'sendNumber',
          align:'left',
          render: (h, params) => {
              var sendNumber=h('div', "计划:"+params.row.sendNumber);
              var finishSendNumber=h('div', "已完成:"+params.row.finishSendNumber);
             return  h('div',[
                    sendNumber,
                    finishSendNumber,
             ]);
          }
        },
        {
            title:'广告状态/创建时间/更新时间',
            title:'广告状态',
           /*   children:[
              {
                  title:'创建时间',
                  minWidth:100,
                  align:'center',
                children:[
                {
                    title:'更新时间',
                    minWidth:100,
                    align:'center',
                },

              ]
              },
          ], */
            key:'status',
             minWidth:100,
          align:'left',
          render: (h, params) => {
               var sendNumber=h('div', ""+params.row.sendNumber);
              var createDate=h('div', ""+params.row.createDate.substr(5));
              var updateDate=h('div', ""+params.row.updateDate.substr(5));
             return  h('div',[
                    sendNumber,
                    createDate,
                    updateDate,
             ]);
          }
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
       countUrl:'/ad/count',
       listUrl:'/ad/list',
       data:'adList'
     },this.params)
    },
  //增加
	 add (params) {
      this.addAdModel = true
      this.addAd.name = params.name
    },
		//增加取消
		 addCancel () {
      if (!this.addLoading) {
        this.addAdModel = false
        this.$refs.addAd.resetFields()
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
      ref:'addAd',
      url:'/ad/add',
      requestObject:'addAd',
      loading:'addLoading',
      showModel:'addAdModel'
    })
    },
	 update (params) {
      this.updateAdModel = true
      //获取修改实体
      this.axiosbusiness.get(this,{
         url:'/ad/load?adId='+params.adId,
         data:'updateAd',
       })
    },
		//修改取消
		 updateCancel () {
      if (!this.updateLoading) {
        this.updateAdModel = false
        this.$refs.updateAd.resetFields()
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
      ref:'updateAd',
      url:'/ad/update',
      requestObject:'updateAd',
      loading:'updateLoading',
      showModel:'updateAdModel'
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
    this.deleteAd={
      "adId":params.adId
    };
    this.axiosbusiness.delete(this,{
      url:'/ad/delete',
      requestObject:'deleteAd'
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

<!--订单管理 -->
<template>
    <div class="body-wrap">
        <div style="color:green;">
            备注：下单人id即为账户id
        </div>
      <div class="body-btn-wrap">
        <div class="search-wrap">
          <Input v-model="params.accountId" class="search-wrap-input" placeholder="下单人id"></Input>
          <Select v-model="params.status" transfer class="search-wrap-input"  placeholder="状态，全部">
              <Option v-for="item in statusParamsList" :value="item.id" :key="item.id">{{ item.value }}</Option>
          </Select>
          <Button @click="search" type="info"  >查询</Button>
        </div>
      </div>
   
      <Table border  :columns='merOrderColumns' :data='merOrderList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;' @on-page-size-change="onPageSizeChange" show-sizer :page-size-opts='params.pageSizeOpts' :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
    
</template>
<script>
export default {
  name: 'MerOrder',
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
      //订单状态，1待发货，2已发货
      statusList:[
        {id:1,value:'待发货'},
        {id:2,value:'已发货'}
      ],
      //查询状态
      statusParamsList:[
        {id:'',value:'全部'},
        {id:1,value:'待发货'},
        {id:2,value:'已发货'}
      ],//修改参数
			updateMerOrderModel:false,
			updateLoading:false,
			updateMerOrderRules: {
                name: [
                    {required: true, message: '商品订单名称为必填项', trigger: 'blur'}
                    ]
                },
			updateMerOrder:{
    		 "merOrderId":1,
      },
      //删除参数
      deleteMerOrder:{},
	    merOrderList: [],
	    merOrderColumns: [
        {
          title: '序号',
          align:'center',
          render: (h, params) => {
            return h('span', params.index
            +(this.params.currentPage-1)*this.params.pageSize+this.params.startNum);
          }
        },
        {
          title: '订单id',
          key: 'merOrderId',
          align:'center'
        },
        {
          title: '订单号',
          key: 'orderNumber',
          align:'center'
        },
        {
          title: '下单人id',
          key: 'accountId',
          align:'center'
        },
       {
        	title:'状态',
            key:'status',
            align:'center',
          render: (h, params) => {
            let statusvalue="";
            let resulth;
            this.statusList.forEach(element => {
              if(element.id==params.row.status){
                statusvalue=element.value;
              }
            });
                resulth=h('span', statusvalue);
             return  resulth;
          }
        },
        {
          title:'商品名称',
          align:'center',
          render: (h, params) => {
            let resulth;
             resulth=h('span', params.row.merOrderDetailList[0].merName);
             return  resulth;
          }
        },
        {
          title:'订单封面',
          align:'center',
          render: (h, params) => {
             return h('img', {
              attrs: {
                src: params.row.merOrderDetailList[0].imgAddress
              },
              style: {
                width: '45px'
              }
            })
          }
        },
        {
          title:'收货地址姓名',
          align:'center',
          render: (h, params) => {
            let resulth;
             resulth=h('span', params.row.merOrderDetailList[0].name);
             return  resulth;
          }
        },
        {
          title:'收货地址手机号',
          align:'center',
          render: (h, params) => {
            let resulth;
             resulth=h('span', params.row.merOrderDetailList[0].phone);
             return  resulth;
          }
        },
        {
          title:'收货地址',
          align:'center',
          render: (h, params) => {
            let resulth;
             resulth=h('span', params.row.merOrderDetailList[0].address);
             return  resulth;
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
                    this.consignment(params.row)
                  }
                }
              }, '发货');
			var s=h("div",[
              varhh1,
            ]);
            if(params.row.status==1){
            return s;
            }else{
              return h("div","");
            }
          }
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
       countUrl:'/merOrder/count',
       listUrl:'/merOrder/list',
       data:'merOrderList'
     },this.params)
    },
    //发货
  consignment(merOrder){
    merOrder.status=2//订单状态，1待发货，2已发货
    let newMerOrder=merOrder;
    delete newMerOrder.merOrderDetailList
     this.axios({
               method:"post",
               url:'/merOrder/update',
               withCredentials: true,
               data: this.Qs.stringify(newMerOrder)
            }).
            then(res => {
              this.loading = false
              if (res.data.code === 200) {
                this.$Message.success(res.data.msg)
              } else {
                this.$Message.error(res.data.msg)
                this.loading = false
              }
            }).catch(res => {
              this.loading = false
              this.$Message.error('系统异常')
            }) 
  }
  },
  created () {
    this.getList();
  },
  mounted () {

  }
}
</script>

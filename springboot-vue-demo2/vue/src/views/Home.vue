<template>
  <!--  tableData要传的值 prop属性对应对象属性 label是表格名称 width不写自适应-->
  <div style="padding: 10px">
    <!--    功能区域-->
    <div style="margin: 10px">
      <el-button type="primary" v-on:click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
    <!--    搜索区域-->
    <div style="margin: 10px">
      <el-input v-model="search" placeholder="请输入关键字" style="width: 20%"></el-input>
      <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>
    </div>
    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%">
      <el-table-column
          prop="id"
          label="ID"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户名">
      </el-table-column>
      <el-table-column
          prop="nickName"
          label="昵称">
      </el-table-column>
      <el-table-column
          prop="age"
          label="年龄">
      </el-table-column>
      <el-table-column
          prop="sex"
          label="性别">
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="180">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" type="success">编辑</el-button>
          <el-popconfirm
              confirmButtonText='好的'
              cancelButtonText='不用了'
              icon="el-icon-info"
              iconColor="red"
              title="这是一段内容确定删除吗？"
          >
            <template #reference>
              <el-button type="danger" @click="deleted(scope.$index,scope.row)">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin:10px 0;float: right">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          :page-sizes="[1,2,5,10]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
      >
      </el-pagination>

      <el-dialog
          title="提示"
          v-model="dialogVisible"
          width="30%">
        <el-form ref="form" :model="form" label-width="30%">
          <el-form-item label="用户名">
            <el-input v-model="form.username" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="form.nickName" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="form.sex" style="width: 80%">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
              <el-radio label="未知">未知</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="住址">
            <el-input type="textarea" v-model="form.address" style="width:80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script>
// @ is an alias to /src

import request from "../utils/request";

export default {
  name: 'Home',
  methods: {
    //删除没有根据确认进行删除
    deleted(index,row) {
      request.delete("/api/user/delete/" + row.id).then(res => {
            console.log(res)
          }
      )
      this.load()
    },
    load() {
      request.get("/api/user", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      });
    },
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    save() {
      if (this.form.id === null || this.form.id === 0) {
        request.post("/api/user", this.form).then(res => {
          console.log(res)
        });
      } else {
        request.post("/api/user/edit", this.form).then(res => {
          console.log(res)
        });
      }
      this.dialogVisible = false
      this.load()
    },
    handleEdit(a) {
      console.log(a);
      this.dialogVisible = true
      this.form = a
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      console.log(this.pageSize)
      this.load()
    },
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.load()
    }
  },
  created() {//页面加载时候调用的方法
    this.load();
  },
  data() {
    return {
      form: {},
      dialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      search: "",
      tableData: []
    }
  }
}
</script>

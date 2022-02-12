<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="奖品名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入奖品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="奖品信息" prop="info">
        <el-input
          v-model="queryParams.info"
          placeholder="请输入奖品信息"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否中奖" prop="win">
        <el-select v-model="queryParams.win" placeholder="请选择是否中奖" clearable size="small">
          <el-option
            v-for="dict in dict.type.cj_win"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['cj:prize:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['cj:prize:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['cj:prize:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['cj:prize:export']"
          >导出</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="prizeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="奖品名称" align="center" prop="name" />
      <el-table-column label="奖品信息" align="center" prop="info" />

      <el-table-column label="背景图片" width="100">
        <template slot-scope="scope">
          <img
            :src="scope.row.backImg"
            width="40"
            height="40"
            class="head_pic"
          />
        </template>
      </el-table-column>

      <el-table-column label="奖品数量" align="center" prop="stock" />
      <el-table-column label="备注信息" align="center" prop="remark" />
      <el-table-column label="是否中奖" align="center" prop="win">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.cj_win" :value="scope.row.win"/>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['cj:prize:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['cj:prize:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改奖品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="奖品名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入奖品名称" />
        </el-form-item>
        <el-form-item label="奖品信息" prop="info">
          <el-input v-model="form.info" placeholder="请输入奖品信息" />
        </el-form-item>
        <el-form-item label="背景图片" prop="backImg">
          <el-image :src="form.backImg">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
          <el-upload
            action="#"
            :http-request="requestUpload"
            :show-file-list="false"
            :before-upload="beforeUpload"
          >
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="奖品数量" prop="stock">
          <el-input v-model="form.stock" placeholder="请输入奖品数量" />
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注信息" />
        </el-form-item>
        <el-form-item label="是否中奖" prop="win">
          <el-radio-group v-model="form.win">
            <el-radio v-for="dict in dict.type.cj_win" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import store from "@/store";
import {
  listPrize,
  getPrize,
  delPrize,
  addPrize,
  updatePrize,
} from "@/api/cj/prize";

export default {
  name: "Prize",
  dicts: ['cj_win'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 奖品表格数据
      prizeList: [],
      // 弹出层标题
      title: "",
      img: "", //裁剪图片的地址
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        info: null,
        win: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "奖品名称不能为空", trigger: "blur" },
        ],
        stock: [
          { required: true, message: "奖品数量不能为空", trigger: "blur" },
        ],
        win: [
          { required: true, message: "是否中奖", trigger: "blur" }
        ]
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 覆盖默认的上传行为
    requestUpload() {},
    // 上传预处理
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.$modal.msgError(
          "文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。"
        );
      } else {
        const isLt1M = file.size / 1024 < 60;
        if (!isLt1M) {
          this.$message.error("上传头像图片大小不能超过 60kb!");
          return;
        }
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.form.backImg = reader.result;
        };
      }
    },
    /** 查询奖品列表 */
    getList() {
      this.loading = true;
      listPrize(this.queryParams).then((response) => {
        this.prizeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        info: null,
        backImg: null,
        stock: null,
        remark: null,
        win: "Y"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加奖品";
      this.form.backImg =
        "data:image/jpeg;base64,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";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPrize(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改奖品";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updatePrize(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPrize(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除奖品编号为"' + ids + '"的数据项？')
        .then(function () {
          return delPrize(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "cj/prize/export",
        {
          ...this.queryParams,
        },
        `prize_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style scoped lang="scss">
.head_pic {
  width: 80px;
  height: 80px;
}
</style>

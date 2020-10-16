<template>
  <div class="content">
    <div class="col-md-8 ml-auto mr-auto">
      <h2 class="text-center">Optimization Data Pipeline</h2>
      <p class="text-center">
        Super Fast Data Sharing Among MM Serivces.
      <!-- With a selection of custom components & and Element UI components, you
        can built beautiful data tables. For more info check
        <a
          href="http://element.eleme.io/#/en-US/component/table"
          target="_blank"
          >Element UI Table</a
        >
       -->
      </p>
    </div>
    <!-- edit modal -->
    <modal
      :show.sync="modals.showEditModal"
      footerClasses="justify-content-center"
      type="notice"
    >
      <h5 slot="header" class="modal-title">
        Label Details
      </h5>
      <div class="instruction">
        <div class="row">
          <div class="col-md-6">
            <strong>Field Name</strong>
              <input type="text" class="form-control" placeholder="Field Name" v-model="modals.currentRow.field">            
          </div>
          <div class="col-md-4">
              <strong>Enabled</strong>
            <div class="row enabled-row">
              <span class="label-switch">false</span>
            <base-switch v-model="modals.currentRow.enabled">
            </base-switch>
            <span class="label-switch label-right">true</span>
            </div>
          </div>
        </div>
        <div class="row margin-top-20x">
          <div class="col-md-6">
            <strong>Operation</strong>
            <el-select
                  class="select-primary"
                  size="large"
                  placeholder="Choose an Operation"
                  v-model="modals.currentRow.operation"
                >
                  <el-option
                    v-for="option in selects.operations"
                    class="select-primary"
                    :value="option.value"
                    :label="option.label"
                    :key="option.label"
                  >
                  </el-option>
            </el-select>
          </div>
          <div class="col-md-4">
              <strong>Experimental</strong>
            <div class="row enabled-row">
              <span class="label-switch">false</span>
            <base-switch v-model="modals.currentRow.experimental">
            </base-switch>
            <span class="label-switch label-right">true</span>
            </div>
          </div>

        </div>        
      
        <div class="row margin-top-20x">
          <div class="col-md-6">
            <strong>Field Source</strong>
            <el-select
                  class="select-primary"
                  size="large"
                  placeholder="Choose a Source"
                  v-model="modals.currentRow.source"
                >
                  <el-option
                    v-for="option in selects.sources"
                    class="select-primary"
                    :value="option.value"
                    :label="option.label"
                    :key="option.label"
                  >
                  </el-option>
            </el-select>
          </div>
        </div>
      </div>
      <div slot="footer" class="justify-content-center footer-bottom-margin margin-top-20x">
        <base-button
          type="info"
          round
          @click.native="editLabel"
          >Save!
        </base-button>
      </div>
    </modal>
    <!-- end of edit modal -->
    <!-- end of add modal -->
    <modal
      :show.sync="modals.showAddModal"
      footerClasses="justify-content-center"
      type="notice"
    >
      <h5 slot="header" class="modal-title">
        Label Details
      </h5>
      <div class="instruction">
        <div class="row">
          <div class="col-md-6">
            <strong>Field Name</strong>
              <input type="text" class="form-control" placeholder="Field Name" v-model="modals.currentRow.field">            
          </div>
          <div class="col-md-4">
              <strong>Enabled</strong>
            <div class="row enabled-row">
              <span class="label-switch">false</span>
            <base-switch v-model="modals.currentRow.enabled">
            </base-switch>
            <span class="label-switch label-right">true</span>
            </div>
          </div>
        </div>
        <div class="row margin-top-20x">
          <div class="col-md-6">
            <strong>Operation</strong>
            <el-select
                  class="select-primary"
                  size="large"
                  placeholder="Choose an Operation"
                  v-model="modals.currentRow.operation"
                >
                  <el-option
                    v-for="option in selects.operations"
                    class="select-primary"
                    :value="option.value"
                    :label="option.label"
                    :key="option.label"
                  >
                  </el-option>
            </el-select>
          </div>
          <div class="col-md-4">
              <strong>Experimental</strong>
            <div class="row enabled-row">
              <span class="label-switch">false</span>
            <base-switch v-model="modals.currentRow.experimental">
            </base-switch>
            <span class="label-switch label-right">true</span>
            </div>
          </div>

        </div>
        <div class="row margin-top-20x">
          <div class="col-md-6">
            <strong>Field Source</strong>
            <el-select
                  class="select-primary"
                  size="large"
                  placeholder="Choose a Source"
                  v-model="modals.currentRow.source"
                >
                  <el-option
                    v-for="option in selects.sources"
                    class="select-primary"
                    :value="option.value"
                    :label="option.label"
                    :key="option.label"
                  >
                  </el-option>
            </el-select>
        </div>
        </div>
      </div>
      <div slot="footer" class="justify-content-center footer-bottom-margin">
        <base-button
          type="info"
          round
          @click.native="addLabel"
          >Add!
        </base-button>
      </div>
    </modal>
    <!-- end of add modal -->
    <div class="row mt-5">
          
    </div>
    <div class="row mt-5">
      <div class="col-12">
        <card card-body-classes="table-full-width">
          <h4 slot="header" class="card-title">Lables</h4>
          <div>
            <div
              class="col-12 d-flex justify-content-center justify-content-sm-between flex-wrap"
            >
              <el-select
                class="select-primary mb-3 pagination-select"
                v-model="pagination.perPage"
                placeholder="Per page"
              >
                <el-option
                  class="select-primary"
                  v-for="item in pagination.perPageOptions"
                  :key="item"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            
              <base-input>
                <el-input
                  type="search"
                  class="mb-3 search-input"
                  clearable
                  prefix-icon="el-icon-search"
                  placeholder="Search records"
                  v-model="searchQuery"
                  aria-controls="datatables"
                >
                </el-input>
              </base-input>
            </div>
            <el-table :data="queriedData">
              <el-table-column
                v-for="column in tableColumns"
                :key="column.label"
                :min-width="column.minWidth"
                :prop="column.prop"
                :label="column.label"
              >
              </el-table-column>
              <el-table-column :min-width="135" align="right" label="Actions">
                <div slot-scope="props">
                  <base-button
                    @click.native="showEditModal(props.$index, props.row)"
                    class="edit btn-link"
                    type="warning"
                    size="sm"
                    icon
                  >
                    <i class="tim-icons icon-pencil"></i>
                  </base-button>
                  <base-button
                    @click.native="handleDisable(props.$index, props.row)"
                    class="remove btn-link"
                    type="danger"
                    size="sm"
                    icon
                  >
                    <i class="tim-icons icon-simple-remove"></i>
                  </base-button>
                </div>
              </el-table-column>
            </el-table>
          </div>
          <div
            slot="footer"
            class="col-12 d-flex justify-content-center justify-content-sm-between flex-wrap"
          >
            <div class="">
              <p class="card-category">
                Showing {{ from + 1 }} to {{ to }} of {{ total }} entries
              </p>
            </div>
            <base-pagination
              class="pagination-no-border"
              v-model="pagination.currentPage"
              :per-page="pagination.perPage"
              :total="total"
            >
            </base-pagination>
          </div>
        </card>
      <div class="d-flex justify-content-end">
          <base-button type="info" round class="float-right" title=""  
          @click.native="showAddNewModal"
          >
            Add New Label <i class="tim-icons icon-minimal-right"></i>
          </base-button>
        </div>
      </div>
      
    </div>
    </div>
    </template>
<script>
import { Table, TableColumn, Select, Option } from 'element-ui';
import { BasePagination } from 'src/components';
/*** import users from './users'; */
import { MANGO_HTTP } from '../../http-common';
import Fuse from 'fuse.js';
import swal from 'sweetalert2';
import { BaseSwitch, Modal } from 'src/components';
 
export default {
  components: {
    BasePagination,
    BaseSwitch,
    Modal,
    [Select.name]: Select,
    [Option.name]: Option,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  computed: {
    /***
     * Returns a page from the searched data or the whole data. Search is performed in the watch section below
     */
    queriedData() {
      let result = this.tableData;
      if (this.searchedData.length > 0) {
        result = this.searchedData;
      }
      return result.slice(this.from, this.to);
    },
    to() {
      let highBound = this.from + this.pagination.perPage;
      if (this.total < highBound) {
        highBound = this.total;
      }
      return highBound;
    },
    from() {
      return this.pagination.perPage * (this.pagination.currentPage - 1);
    },
    total() {
      return this.searchedData.length > 0
        ? this.searchedData.length
        : this.tableData.length;
    }
  },
  data() {
    return {
      selects: {
        simple: '',
        operations: [
          { value: 'LOG', label: 'Record in Imp Logs' },
          { value: 'CALL_DELPHI', label: 'Send To Delphi' }
        ],
        sources: [
          { value: 'OPEN_RTB', label: 'Open Rtb Bid Req' },
          { value: 'IMP_LOG', label: 'Impression Log' }
        ]
      },
      modals: {
        classic: false,
        showEditModal: false,
        showAddModal: false,
        mini: false,
        currentRow : {
          id : -1,
          field : "",
          operation : "",
          source : "",
          enabled: false,
          experimental: false
        }
      },
      pagination: {
        perPage: 5,
        currentPage: 1,
        perPageOptions: [5, 10, 25, 50],
        total: 0
      },
      searchQuery: '',
      tableColumns: [
        {
          prop: 'id',
          label: 'Id',
          minWidth: 40
        },
        {
          prop: 'sourceStr',
          label: 'source',
          minWidth: 100
        },
        {
          prop: 'field',
          label: 'field',
          minWidth: 200
        },
        {
          prop: 'operationStr',
          label: 'operation',
          minWidth: 250
        },
        {
          prop: 'enabledStr',
          label: 'enabled',
          minWidth: 120
        },
        {
          prop: 'experimentalStr',
          label: 'experimental',
          minWidth: 120
        }
      ],
      tableData: [],
      searchedData: [],
      fuseSearch: null
    };
  },
  methods: {
    addLabel() { 
        let self = this;
        MANGO_HTTP.post("label/add", {
          source: self.modals.currentRow.source,
          field: self.modals.currentRow.field,
          operation: self.modals.currentRow.operation,
          enabled: self.modals.currentRow.enabled,
          experimental: self.modals.currentRow.experimental
        }).then( response => {
          console.log("add response", response.data);
          self.getLabelList();
          self.modals.showAddModal = false
      });
    },
    editLabel() { 
        let self = this;
        MANGO_HTTP.post("label/update", {
          id: self.modals.currentRow.id,
          source: self.modals.currentRow.source,
          field: self.modals.currentRow.field,
          operation: self.modals.currentRow.operation,
          enabled: self.modals.currentRow.enabled,
          experimental: self.modals.currentRow.experimental
        }).then( response => {
          console.log("update response", response.data);
          self.getLabelList();
          self.modals.showEditModal = false
      });
    },
    getLabelList () {
      let self = this;
      MANGO_HTTP.get("label/list").then( response => {
          if( response.data ) {
              let list = [];

              response.data.forEach(function (el, i) {
                var enableValue = "False";
                if (el.enabled === true) {
                  enableValue = "True"
                }
                var experimentalValue = "False";
                if (el.experimental === true) {
                  experimentalValue = "True"
                }

                var sourceStr = "";
                if (el.source === 'OPEN_RTB') {
                  sourceStr = "Open Rtb Bid Req"
                } else if (el.source === 'IMP_LOG') {
                  sourceStr = "Impression Log"
                }

                var operationStr = "";
                if (el.operation === 'LOG') {
                  operationStr = "Record in Imp Logs"
                } else if (el.operation === 'CALL_DELPHI') {
                  operationStr = "Send To Delphi"
                }

                list.push({
                  id: el.id,
                  field: el.field,
                  operation: el.operation,
                  operationStr: operationStr,
                  source: el.source,
                  sourceStr: sourceStr,
                  enabled: el.enabled,
                  enabledStr: enableValue,
                  experimental: el.experimental,
                  experimentalStr: experimentalValue
                });
              });
              self.tableData = list;
          }
      });
    },
    showAddNewModal() {
      this.modals.showAddModal = true
      this.modals.currentRow.id = undefined
      this.modals.currentRow.field = ""
      this.modals.currentRow.operation = ""
      this.modals.currentRow.source = ""
      this.modals.currentRow.enabled = false
      this.modals.currentRow.experimental = false
    },
    showEditModal(index, row) {
      this.modals.showEditModal = true
      this.modals.currentRow = row
    },
    handleDisable(index, row) {
      swal.fire({
        title: `Disable ${row.field}?`,
        text: `Are you sure?`,
        icon: 'warning',
        showCancelButton: true,
        customClass: {
          confirmButton: 'btn btn-success btn-fill',
          cancelButton: 'btn btn-danger btn-fill'
        },
        confirmButtonText: 'Yes, disable it!',
        buttonsStyling: false
      }).then(result => {
        if (result.value) {
          /** this.deleteRow(row); */

        let self = this;
        MANGO_HTTP.post("label/enable", {
          id: row.id,
          enabled: false
        }).then( response => {
          self.getLabelList();
          swal.fire({
            title: 'Disabled!',
            text: `You disabled ${row.field}`,
            icon: 'success',
            confirmButtonClass: 'btn btn-success btn-fill',
            buttonsStyling: false
          });
        });
          
        }
      });
    },
    deleteRow(row) {
      let indexToDelete = this.tableData.findIndex(
        tableRow => tableRow.id === row.id
      );
      if (indexToDelete >= 0) {
        this.tableData.splice(indexToDelete, 1);
      }
    }
  },
  mounted() {
    // Fuse search initialization.
    this.fuseSearch = new Fuse(this.tableData, {
      keys: ['source', 'field', 'operation'],
      threshold: 0.3
    });
    document.title = 'Lables';
    this.getLabelList();
  },
  watch: {
    /**
     * Searches through the table data by a given query.
     * NOTE: If you have a lot of data, it's recommended to do the search on the Server Side and only display the results here.
     * @param value of the query
     */
    searchQuery(value) {
      let result = this.tableData;
      console.log("searchQuery : ", this.searchQuery)
      if (value !== '') {
        result = this.fuseSearch.search(this.searchQuery);
      }
      console.log("searchQuery : result ", result)
      this.searchedData = result;

      /** 
      let result = this.tableData;
        console.log("searchQuery : ", this.searchQuery)
        for (i = 0; i < tr.length; i++) {
        }
       */
       
    }
  }
};
</script>
<style>
.pagination-select,
.footer-bottom-margin {
   margin-bottom: 30px;
}
.search-input {
  width: 200px;
}
.swal2-icon-content{
  font-size: inherit !important;
}

.label-switch { 
  margin-left :10px;
  margin-right :10px;
}

.enabled-row1111 { 
  display: flex;
   /** 
   align-content: space-between | space-around | space-evenly; -->
   */ 
   align-content: space-between;
}
.margin-top-20x {
  margin-top : 20px;
}
.modal-content {
  width: 120%;
  height: 140%;
}
</style>

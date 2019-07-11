// select 插件
Vue.component('vm-select', {
    props : ['options', 'value', 'multiple', 'method', 'load', 'index', 'childidx'],
    template : "<select :multiple='multiple' class='selectpicker' data-live-search='true' title='请选择' data-live-search-placeholder='搜索'><option :value='option.value' v-for='option in options'>{{ option.label }}</option></select>",
    mounted : function () {
        var vm = this;
        $(this.$el).selectpicker('val', this.value != null ? this.value : null);
        $(this.$el).on('changed.bs.select', function () {
            vm.$emit('input', $(this).val());
            if (typeof(vm.method) != 'undefined') {
                vm.method(vm.index, vm.childidx, this.value);
            }
        });
        $(this.$el).on('show.bs.select', function () {
            if (typeof(vm.load) != 'undefined') {
                vm.load(vm.index, vm.childidx);
            }
        });
    },
    updated : function () {
        $(this.$el).selectpicker('refresh');
    },
    destroyed : function () {
        $(this.$el).selectpicker('destroy');
    }
});
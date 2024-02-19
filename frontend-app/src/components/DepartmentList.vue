<template>
    <div class="departmentsContainer">
        <a-tree
            :tree-data="treeData"
            @select="setSelectedDepartment"
        >
        </a-tree>
        <div v-if="selectedKey">
            <DepartmentDetails :department="selectedDepartment"/>
        </div>
    </div>

</template>

<script lang="ts" setup>
import {computed, ComputedRef, defineProps, ref, watch} from 'vue';
import {DepartmentModel} from "@/appModel";
import DepartmentDetails from "@/components/DepartmentItem.vue";

const selectedKey = ref<number | null>(null);
const treeData = ref<TreeNode[]>([]);

const props = defineProps({
    departmentList: {
        type: Object as () => Array<DepartmentModel>,
        required: true
    }
});

interface TreeNode {
    title: string;
    key: number;
    children?: TreeNode[];
}

interface SelectedNodeInfo {
    event: string,
    selected: boolean,
    node: TreeNode,
    selectedNodes: []
}

const buildTree = (departments: DepartmentModel[]): TreeNode[] => {
    const departmentMap: Record<number, TreeNode> = {};
    departments.forEach(department => {
        departmentMap[department.id] = {
            title: department.name,
            key: department.id,
            children: []
        };
    });

    departments.forEach(department => {
        if (department.parentDepartmentId !== null) {
            const parentDepartment = departmentMap[department.parentDepartmentId];
            if (parentDepartment?.children) {
                parentDepartment.children.push(departmentMap[department.id]);
            }
        }
    });

    return departments
        .filter(department => department.parentDepartmentId === null)
        .map(rootDepartment => departmentMap[rootDepartment.id]);
};

const selectedDepartment: ComputedRef<DepartmentModel | undefined> = computed(() => {
    const id = selectedKey.value;
    const department: DepartmentModel | undefined = props.departmentList.find((item: DepartmentModel) => item.id === id);
    return department;
});

const setSelectedDepartment = (selectedKeys: Array<string>, info: SelectedNodeInfo) => {
    selectedKey.value = info.node?.key;
}

watch(props, () => {
    treeData.value = buildTree(props.departmentList);
}, {immediate: true});

</script>

<style>
.departmentsContainer {
    display: flex;
    flex-direction: row;
}
</style>

import { defineStore } from "pinia";
import { ref } from "vue";

export const useGlobalState = defineStore("state", () => {
  const clusterData = ref<Array<string | object>>([]);
  const clusterTreeData = ref();
  const clusterMetaData = ref();
  const updateClusterData = (newData: string[] | object[]) => {
    clusterData.value = newData;
  };

  return { clusterData, clusterTreeData, clusterMetaData, updateClusterData };
});

export default useGlobalState;

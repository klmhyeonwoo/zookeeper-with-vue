import { api } from "../../../api/index";
import { useQuery } from "@tanstack/vue-query";

type clusterTreeProps = {
  [index: string]: string[];
};

export const useApiGetClusterTree = () => {
  const getClusterTree = async (clusterName: string, searchQuery: string) => {
    console.log("함수 실행 :", clusterName);
    const res = await api.get(
      `/api/zkui/clusters/${clusterName}/tree?path=${searchQuery}`,
    );
    return res.data as clusterTreeProps;
  };

  return (clusterName: string, searchQuery: string) =>
    useQuery({
      queryKey: [clusterName, searchQuery, Date.now()],
      queryFn: () => getClusterTree(clusterName, searchQuery),
      gcTime: 0,
    });
};

import { api } from "../../../api";
import { useQuery } from "@tanstack/vue-query";

type clusterTreeProps = {
  [index: string]: string[];
};

export const getClusterTree = async (
  clusterName: string,
  searchQuery: string = "/",
) => {
  const res = await api.get(
    `/api/zkui/clusters/${clusterName}/tree?path=${searchQuery}`,
  );
  return res.data as clusterTreeProps;
};
export const useApiGetClusterTree = () => {
  return (clusterName: string, searchQuery: string) =>
    useQuery({
      queryKey: [clusterName, searchQuery, Date.now()],
      queryFn: () => getClusterTree(clusterName, searchQuery),
      gcTime: 0,
    });
};

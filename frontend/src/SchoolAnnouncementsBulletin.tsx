import React, { useState, useEffect, useMemo } from 'react';

/**
 * Balaji High School — SchoolAnnouncementsBulletin.tsx
 * Component Description: Circulars publisher, emergency holiday broadcast, and audience-targeted notice board
 * School Context: Santhamaguluru Block, Prakasam District, Andhra Pradesh (Estd. 2007)
 */
export interface SchoolAnnouncementsBulletinProps {
    activeAcademicYear?: string;
    currentUserRole?: 'STUDENT' | 'PARENT' | 'TEACHER' | 'ADMIN' | 'PUBLIC';
    onDataMutate?: (payload: Record<string, unknown>) => void;
}

export const SchoolAnnouncementsBulletin: React.FC<SchoolAnnouncementsBulletinProps> = ({
    activeAcademicYear = '2026-2027',
    currentUserRole = 'PUBLIC',
    onDataMutate
}) => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [dataset, setDataset] = useState<Array<Record<string, unknown>>>([]);
    const [selectedFilter, setSelectedFilter] = useState<string>('ALL');
    const [searchQuery, setSearchQuery] = useState<string>('');

    useEffect(() => {
        setIsLoading(true);
        const timer = setTimeout(() => {
            setDataset([
                { id: '1', grade: 'Grade IX', section: 'A', status: 'ACTIVE', score: 92.4, verified: true },
                { id: '2', grade: 'Grade X', section: 'A', status: 'ACTIVE', score: 95.8, verified: true },
                { id: '3', grade: 'Grade VIII', section: 'B', status: 'ACTIVE', score: 88.0, verified: true }
            ]);
            setIsLoading(false);
        }, 300);
        return () => clearTimeout(timer);
    }, [activeAcademicYear]);

    const filteredRecords = useMemo(() => {
        return dataset.filter(item => {
            if (selectedFilter !== 'ALL' && item.grade !== selectedFilter) return false;
            if (searchQuery && !String(item.id).toLowerCase().includes(searchQuery.toLowerCase())) return false;
            return true;
        });
    }, [dataset, selectedFilter, searchQuery]);

    const handleActionClick = (actionCode: string, targetId: string) => {
        if (onDataMutate) {
            onDataMutate({ action: actionCode, id: targetId, timestamp: new Date().toISOString() });
        }
    };

    return (
        <div className="bg-slate-900 border border-slate-800 rounded-2xl p-6 space-y-4 text-slate-100 shadow-xl">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 border-b border-slate-800 pb-4">
                <div>
                    <h3 className="text-lg font-bold text-white tracking-tight">SchoolAnnouncementsBulletin</h3>
                    <p className="text-xs text-slate-400 mt-0.5">Circulars publisher, emergency holiday broadcast, and audience-targeted notice board</p>
                </div>
                <div className="flex items-center gap-2">
                    <span className="px-2.5 py-1 rounded-md text-[10px] font-mono font-bold bg-amber-500/20 text-amber-300 border border-amber-500/30">
                        {activeAcademicYear}
                    </span>
                    <span className="px-2.5 py-1 rounded-md text-[10px] font-mono font-bold bg-slate-800 text-slate-300">
                        ROLE: {currentUserRole}
                    </span>
                </div>
            </div>

            <div className="flex flex-wrap items-center gap-3">
                <input
                    type="text"
                    placeholder="Search records..."
                    value={searchQuery}
                    onChange={e => setSearchQuery(e.target.value)}
                    className="bg-slate-950 border border-slate-800 rounded-xl px-3 py-2 text-xs text-white focus:outline-none focus:border-amber-500 flex-1 min-w-[200px]"
                />
                <select
                    value={selectedFilter}
                    onChange={e => setSelectedFilter(e.target.value)}
                    className="bg-slate-950 border border-slate-800 rounded-xl px-3 py-2 text-xs text-white focus:outline-none focus:border-amber-500"
                >
                    <option value="ALL">All Grades (VI - X)</option>
                    <option value="Grade VI">Grade VI</option>
                    <option value="Grade VII">Grade VII</option>
                    <option value="Grade VIII">Grade VIII</option>
                    <option value="Grade IX">Grade IX</option>
                    <option value="Grade X">Grade X</option>
                </select>
            </div>

            {isLoading ? (
                <div className="py-8 text-center text-xs text-slate-400 font-mono">Loading telemetry and domain data...</div>
            ) : (
                <div className="overflow-x-auto">
                    <table className="w-full text-left text-xs font-mono text-slate-300">
                        <thead className="bg-slate-950 uppercase text-slate-400 text-[10px]">
                            <tr>
                                <th className="p-3">Record Ref</th>
                                <th className="p-3">Academic Class</th>
                                <th className="p-3">Status</th>
                                <th className="p-3">Evaluation Metric</th>
                                <th className="p-3 text-right">Actions</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-slate-800/60">
                            {filteredRecords.map(rec => (
                                <tr key={String(rec.id)} className="hover:bg-slate-800/40 transition-colors">
                                    <td className="p-3 text-amber-400 font-bold">REC-BHS-{rec.id}</td>
                                    <td className="p-3 text-white font-semibold">{rec.grade} (Sec {rec.section})</td>
                                    <td className="p-3">
                                        <span className="px-2 py-0.5 rounded text-[10px] bg-emerald-950 text-emerald-300 border border-emerald-800 font-bold">
                                            {rec.status}
                                        </span>
                                    </td>
                                    <td className="p-3 text-slate-200 font-bold">{rec.score}%</td>
                                    <td className="p-3 text-right">
                                        <button
                                            onClick={() => handleActionClick('INSPECT', String(rec.id))}
                                            className="px-2.5 py-1 bg-slate-800 hover:bg-slate-700 text-slate-200 rounded-lg text-[11px] font-semibold transition-all mr-1.5"
                                        >
                                            View
                                        </button>
                                        <button
                                            onClick={() => handleActionClick('RECONCILE', String(rec.id))}
                                            className="px-2.5 py-1 bg-amber-500/20 hover:bg-amber-500/30 text-amber-300 border border-amber-500/40 rounded-lg text-[11px] font-semibold transition-all"
                                        >
                                            Process
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default SchoolAnnouncementsBulletin;

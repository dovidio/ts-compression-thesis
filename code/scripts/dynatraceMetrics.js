const dynatraceMetrics = [
    'builtin:host.cpu.usage',
    'builtin:host.cpu.user',
    'builtin:host.cpu.system',
    'builtin:host.cpu.iowait',
    'builtin:host.mem.used',
    'builtin:host.mem.avail.bytes',
    'builtin:host.disk.readTime',
    'builtin:host.disk.writeTime'
];

module.exports = {
    dynatraceMetrics: dynatraceMetrics
}